package cn.landis.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class RequestFilter implements Filter {
	private String charset = null;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (charset == null || charset.isEmpty()) {
			charset = "utf-8";
		}
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		if ("post".equalsIgnoreCase(req.getMethod())) {
			req.setCharacterEncoding(charset);
		} else if ("get".equalsIgnoreCase(req.getMethod())) {
			req = new RequestEncodingWrapper(req, charset);
		}
		resp.setContentType("text/html;charset=utf-8");
		chain.doFilter(req, resp);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		charset = filterConfig.getInitParameter("charset");
	}

}