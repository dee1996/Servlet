package cn.landis.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.landis.dao.CommentDao;
import cn.landis.dao.StudentDao;
import cn.landis.dao.impl.CommentDaoImpl;
import cn.landis.dao.impl.StudentDaoImpl;
import cn.landis.entity.CommentEntity;
import cn.landis.entity.StudentEntity;

public class Comment extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uri = request.getRequestURI();
		String action = uri.substring(uri.lastIndexOf("/") + 1,
				uri.lastIndexOf("."));
		CommentDao dao = new CommentDaoImpl();
		if ("listComment".equals(action)) {
			int studentId = Integer.parseInt(request.getParameter("studentId"));
		  	List<CommentEntity> list = dao.getAllComment();
		  	StudentDao sdao = new StudentDaoImpl();
		  	StudentEntity student = sdao.getOne(studentId);
		  	request.setAttribute("list", list);
		  	request.setAttribute("student", student);
		  	request.getRequestDispatcher("comment.jsp").forward(request, response);
		} else if ("comment".equals(action)) {
			int studentId = Integer.parseInt(request.getParameter("studentId"));
			CommentEntity entity = new CommentEntity();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = sdf.format(new Date());
			entity.setCommentDate(date);
			entity.setStudentId(studentId);
			String comment = request.getParameter("comment");
			if (comment == null || comment.equals("")) {
				request.getSession().setAttribute("studentId", studentId);
				request.getSession().setAttribute("alert", "comment");
				response.sendRedirect("alert.jsp");
				return;
			} else {
				entity.setStudentComment(comment);
			}
			boolean flag = dao.addComment(entity);
			if (flag) {
				response.sendRedirect("listComment.co?studentId=" + studentId);
			}
		} else if ("adminComment".equals(action)) {
			CommentEntity entity = new CommentEntity();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = sdf.format(new Date());
			String comment = request.getParameter("comment");
			entity.setCommentDate(date);
			entity.setStudentId(0);
			if (comment == null || comment.equals("")) {
				request.getSession().setAttribute("alert", "admincomment");
				response.sendRedirect("alert.jsp");
				return;
			} else {
				entity.setStudentComment(comment);
			}
			boolean flag = dao.addComment(entity);
			if (flag) {
				response.sendRedirect("adminCommentList.co");
			}
		} else if ("adminCommentList".equals(action)) {
			List<CommentEntity> list = dao.getAllComment();
		  	request.setAttribute("list", list);
		  	request.getRequestDispatcher("adminComment.jsp").forward(request, response);
		}
	}
}