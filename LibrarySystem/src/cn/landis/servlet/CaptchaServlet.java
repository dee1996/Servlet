package cn.landis.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CaptchaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 响应对象设置内容类型为image/jpeg
		response.setContentType("image/jpeg");

		// 创建图片缓冲区
		BufferedImage image = new BufferedImage(100, 35,
				BufferedImage.TYPE_INT_RGB);
		// 创建图片画笔对象
		Graphics graphics = image.getGraphics();
		Random random = new Random();
		// 设置画笔颜色
		graphics.setColor(new Color(random.nextInt(255), random.nextInt(255),
				random.nextInt(255)));
		// 设置背景矩形
		graphics.fillRect(0, 0, 100, 35);
		// 设置验证码内容的字母数字组合字符串
		String captchaContent = getCaptcha(5);
		graphics.setColor(new Color(0, 0, 0));
		graphics.setFont(new Font(null, Font.BOLD, 24));
		// 绘制字符串
		graphics.drawString(captchaContent, 5, 30);
		// 绘制干扰线
		for (int i = 0; i < 4; i++) {
			graphics.setColor(new Color(random.nextInt(255), random
					.nextInt(255), random.nextInt(255)));
			graphics.drawLine(random.nextInt(100), random.nextInt(35),
					random.nextInt(100), random.nextInt(35));
		}

		OutputStream os = response.getOutputStream();
		ImageIO.write(image, "jpeg", os);
		os.flush();
		os.close();

		// 把生成的验证码内容放在Session对象中
		HttpSession session = request.getSession();
		session.setAttribute("captcha", captchaContent);
	}

	/**
	 * 获取验证码内容的字母数字组合字符串
	 * 
	 * @param size
	 *            验证码长度
	 * @return
	 */
	private String getCaptcha(Integer size) {
		String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String result = "";

		Random random = new Random();
		for (int i = 0; i < size; i++) {
			result += str.charAt(random.nextInt(str.length()));
		}

		return result;
	}
}
