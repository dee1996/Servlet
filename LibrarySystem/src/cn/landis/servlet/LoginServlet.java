package cn.landis.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.landis.dao.LoginDao;
import cn.landis.dao.impl.LoginImpl;
import cn.landis.entity.AdminEntity;
import cn.landis.entity.StudentEntity;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		HttpSession session = request.getSession();
		String uri = request.getRequestURI();
		String url = uri.substring(uri.lastIndexOf("/") + 1,
				uri.lastIndexOf("."));
		if ("adminLogin".equals(url)) {
			String captcha = request.getParameter("captcha");
			if (!captcha.equalsIgnoreCase(session.getAttribute("captcha")
					.toString())) {
				request.setAttribute("msg", "验证码错误！");
				// 转发
				request.getRequestDispatcher("index.jsp").forward(request,
						response);
			} else {
				AdminEntity admin = new AdminEntity();
				String username = request.getParameter("username");
				admin.setUsername(username);
				String password = request.getParameter("password");
				admin.setPassword(password);
				request.getSession().setAttribute("username", username);
				request.getSession().setAttribute("password", password);
				LoginDao dao = new LoginImpl();
				if (dao.adminLogin(admin) == null) {
					request.setAttribute("mas", "账号或密码错误");
					request.getRequestDispatcher("index.jsp").forward(request,
							response);
				} else {
					session.setMaxInactiveInterval(30 * 60);
					session.setAttribute("admin", admin);
					request.getRequestDispatcher("admin.jsp").forward(
							request, response);
				}
			}
		}
		if ("stuLogin".equals(url)) {
			String captcha = request.getParameter("captcha");
			if (!captcha.equalsIgnoreCase(session.getAttribute("captcha")
					.toString())) {
				request.setAttribute("msg", "验证码错误！");
				// 转发
				request.getRequestDispatcher("index.jsp").forward(request,
						response);
			} else {
				StudentEntity student = new StudentEntity();
				String username = request.getParameter("username");
				student.setUsername(username);
				String password = request.getParameter("password");
				student.setPassword(password);
				request.getSession().setAttribute("username", username);
				request.getSession().setAttribute("password", password);
				LoginDao dao = new LoginImpl();
				if (dao.stuLogin(student) == null) {
					request.setAttribute("mas", "账号或密码错误");
					request.getRequestDispatcher("index.jsp").forward(request,
							response);
				} else {
					student = dao.stuLogin(student);
					session.setMaxInactiveInterval(30 * 60);
					request.getSession().setAttribute("student", student);
					request.getRequestDispatcher("student.jsp").forward(
							request, response);
				}
			}
		}
	}
}