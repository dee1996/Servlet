package cn.landis.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.landis.dao.BookDao;
import cn.landis.dao.BorrowDao;
import cn.landis.dao.impl.BookDaoImpl;
import cn.landis.dao.impl.BorrowDaoImpl;
import cn.landis.entity.BookEntity;
import cn.landis.entity.BorrowEntity;
import cn.landis.entity.StudentEntity;
import cn.landis.model.PageModel;
import cn.landis.util.GetBookDetail;

public class Borrow extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		StudentEntity student = (StudentEntity) request.getSession()
				.getAttribute("student");
		String uri = request.getRequestURI();
		BookDao dao = new BookDaoImpl();
		String action = uri.substring(uri.lastIndexOf("/") + 1,
				uri.lastIndexOf("."));
		if ("listborrow".equals(action)) {
			String[] fileds = null;
			String filed = null;
			if (request.getParameterValues("fileds") != null) {
				fileds = request.getParameterValues("fileds");
				filed = fileds[0];
			}
			if (request.getParameter("filed") != null) {
				filed = request.getParameter("filed");
			}
			String content = null;
			if (request.getParameter("content") != null) {
				content = request.getParameter("content");
			}
			Map<String, String> condition = new HashMap<String, String>();
			condition.put(filed, content);
			String temp = request.getParameter("pageNo");
			Integer pageNo = null;
			if (temp == null) {
				pageNo = 1;
			} else {
				pageNo = Integer.parseInt(temp);
			}
			PageModel pageModel = new PageModel();
			List<BookEntity> list = dao.findByCondition(condition, pageNo, 3);
			pageModel.setList(list);
			pageModel.setPageNo(pageNo);
			pageModel.setPageSize(3);
			pageModel.setTotalCount((int) dao.getTotalCountByCondition(condition));
			request.getSession().setAttribute("filed", filed);
			request.getSession().setAttribute("content", content);
			request.setAttribute("pageModel", pageModel);
			request.getRequestDispatcher("borrow.jsp").forward(request,
					response);
		} else if ("borrowbook".equals(action)) {

			Integer studentId = student.getId();
			if (studentId == null) {
				request.getRequestDispatcher("login.jsp").forward(request,
						response);
			}

			Integer bookId = Integer.parseInt(request.getParameter("id"));
			BookEntity book = dao.getOne(bookId);
			BorrowEntity borrow = new BorrowEntity();
			BorrowDao borrowDao = new BorrowDaoImpl();

			borrow.setStudentId(studentId);
			borrow.setStudentName(student.getUsername());
			borrow.setBookId(bookId);
			borrow.setBookName(book.getName());

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String startDate = sdf.format(date);
			String endDate = sdf.format(getDateAfter(new Date(), 30));
			borrow.setStartDate(startDate);
			borrow.setEndDate(endDate);
			borrow.setStudentStatus(0);
			borrow.setHasReturn("未逾期");
			borrow.setMoney(0);
			if (borrowDao.checkBorrow(studentId, bookId)) {
				if (dao.updateByBorrow(bookId, book.getCount() - 1)) {
					if (borrowDao.addBorrow(borrow)) {
						request.getSession().setAttribute("alert", "borrowBook");
						request.getRequestDispatcher("alert.jsp").forward(request, response);
					}
				} else {
					request.getSession().setAttribute("alert", "borrowBookError");
					request.getRequestDispatcher("alert.jsp").forward(request, response);
				}
			} else {
				request.getSession().setAttribute("alert", "hasBorrow");
				response.sendRedirect("alert.jsp");
				return;
			}
		} else if ("returnbooklist".equals(action)) {
			BorrowDao borrowDao = new BorrowDaoImpl();
			String[] fileds = null;
			String filed = null;
			if (request.getParameterValues("fileds") != null) {
				fileds = request.getParameterValues("fileds");
				filed = fileds[0];
			}
			if (request.getParameter("filed") != null) {
				filed = request.getParameter("filed");
			}
			String content = null;
			if (request.getParameter("content") != null) {
				content = request.getParameter("content");
			}
			Map<String, String> condition = new HashMap<String, String>();
			condition.put(filed, content);
			String temp = request.getParameter("pageNo");
			Integer pageNo = null;
			if (temp == null) {
				pageNo = 1;
			} else {
				pageNo = Integer.parseInt(temp);
			}
			PageModel pageModel = new PageModel();
			List<BorrowEntity> list = borrowDao.getAllBorrow(condition, pageNo,
					3);
			pageModel.setList(list);
			pageModel.setPageNo(pageNo);
			pageModel.setPageSize(3);
			pageModel.setTotalCount((int) borrowDao.getTotalCountByCondition(condition));
			request.getSession().setAttribute("filed", filed);
			request.getSession().setAttribute("content", content);
			request.setAttribute("pageModel", pageModel);
			request.getRequestDispatcher("hasBorrow.jsp").forward(request,
					response);
		} else if ("returnbook".equals(action)) {

			Integer studentId = Integer.parseInt(request.getParameter("studentId"));
			Integer bookId = Integer.parseInt(request.getParameter("bookId"));
			BookEntity book = dao.getOne(bookId);
			BorrowEntity borrow = new BorrowEntity();
			BorrowDao borrowDao = new BorrowDaoImpl();

			borrow.setStudentId(studentId);
			borrow.setBookId(bookId);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String endDate = sdf.format(new Date());
			borrow.setEndDate(endDate);
			borrow.setStudentStatus(1);
			borrow.setHasReturn("已归还");
			String start = borrowDao.getStartDate(bookId, studentId);
			
			Date startDate = new Date();
			try {
				startDate = sdf.parse(start);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Date nowDate = new Date();
			long tempTime = nowDate.getTime() - startDate.getTime();
			int temp = (int)(tempTime / (24 * 60 * 60 * 1000));
			if(temp <= 30) {
				borrow.setMoney(0);
			}
			 else if(temp > 30) { 
				 borrow.setMoney((temp - 30) * 1);
			} 
			if (borrowDao.updateByReturn(borrow)) {
				if (dao.updateByBorrow(bookId, book.getCount() + 1)) {
					request.getSession().setAttribute("alert", "returnBook");
					request.getRequestDispatcher("alert.jsp").forward(request, response);
				}
			} else {
				request.getSession().setAttribute("alert", "returnBookError");
				request.getRequestDispatcher("alert.jsp").forward(request, response);
			}
		} else if ("listMyBorrow".equals(action)) {
			Integer studentId = Integer.parseInt(request.getParameter("studentId"));
			BorrowDao borrowDao = new BorrowDaoImpl();
			String[] fileds = null;
			String filed = null;
			if (request.getParameterValues("fileds") != null) {
				fileds = request.getParameterValues("fileds");
				filed = fileds[0];
			}
			if (request.getParameter("filed") != null) {
				filed = request.getParameter("filed");
			}
			String content = null;
			if (request.getParameter("content") != null) {
				content = request.getParameter("content");
			}
			Map<String, String> condition = new HashMap<String, String>();
			condition.put(filed, content);
			String temp = request.getParameter("pageNo");
			Integer pageNo = null;
			if (temp == null) {
				pageNo = 1;
			} else {
				pageNo = Integer.parseInt(temp);
			}
			PageModel pageModel = new PageModel();
			List<BorrowEntity> list = borrowDao.getStudentBorrow(condition, pageNo, 3 ,studentId);
			pageModel.setList(list);
			pageModel.setPageNo(pageNo);
			pageModel.setPageSize(3);
			pageModel.setTotalCount((int)borrowDao.getTotalCountByCondition(condition));
			request.getSession().setAttribute("filed", filed);
			request.getSession().setAttribute("studentId", studentId);
			request.getSession().setAttribute("content", content);
			request.setAttribute("pageModel", pageModel);
			request.getRequestDispatcher("listMyBorrow.jsp").forward(request,
					response);
		} else if ("bookDetail".equals(action)) {
			int id = Integer.parseInt(request.getParameter("bookId"));
			BookEntity book = dao.getOne(id);
			int borrowCount = (int)dao.getBorrowCount(id);
			GetBookDetail detail = new GetBookDetail();
			List<String> list = new ArrayList<String>();
			try {
				list = detail.getList(book.getName());
			} catch (Exception e) {
				e.printStackTrace();
			}
			request.setAttribute("borrowCount", borrowCount);
			request.setAttribute("book", book);
			request.setAttribute("list", list);
			request.getRequestDispatcher("bookDetail.jsp").forward(request, response);
		} 
	}

	public static Date getDateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}
}