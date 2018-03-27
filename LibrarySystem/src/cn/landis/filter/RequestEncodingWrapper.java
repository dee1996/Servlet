package cn.landis.filter;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class RequestEncodingWrapper extends HttpServletRequestWrapper {
	// 成员变量
	private String charset = null;

	public RequestEncodingWrapper(HttpServletRequest request) {
		super(request);
	}

	public RequestEncodingWrapper(HttpServletRequest request, String charset) {
		super(request);
		this.charset = charset;
	}

	/**
	 * 单个参数重新编码
	 */
	@Override
	public String getParameter(String name) {
		String value = super.getParameter(name);

		if (value != null) {
			value = changeEncoding(value, "ISO8859-1", charset);
		}

		return value;
	}

	/**
	 * 
	 */
	@Override
	public String[] getParameterValues(String name) {
		String[] values = super.getParameterValues(name);

		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				values[i] = changeEncoding(values[i], "ISO8859-1", charset);
			}
		}

		return values;
	}

	/**
	 * 编码转换函数
	 * 
	 * @param input
	 *            输入的字符串
	 * @param oldEncoding
	 *            老的编码格式
	 * @param newEncoding
	 *            新的编码格式
	 * @return
	 */
	public String changeEncoding(String input, String oldEncoding,
			String newEncoding) {
		byte[] arr = null;

		try {
			// 1、按老的编码格式转换为byte数组
			arr = input.getBytes(oldEncoding);
			// 2、通过String的构造函数转换为新的编码格式的字符串
			return new String(arr, newEncoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return input;
	}
}