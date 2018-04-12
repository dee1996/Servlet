package cn.landis.servlet;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.landis.dao.BookDao;
import cn.landis.dao.impl.BookDaoImpl;
import cn.landis.entity.BookEntity;
import cn.landis.model.PageModel;

public class Book extends HttpServlet {
	public File uploadPath = null;

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uri = request.getRequestURI();
		BookDao dao = new BookDaoImpl();
		String action = uri.substring(uri.lastIndexOf("/") + 1,
				uri.lastIndexOf("."));
		if ("bookadd".equals(action)) {
			BookEntity book = new BookEntity();
			boolean isMultiPart = ServletFileUpload.isMultipartContent(request);
			String category = "";
			String name = "";
			String description = "";
			String image = "";
			Integer count = null;
			if (isMultiPart) {
				try {
					FileItemFactory factory = new DiskFileItemFactory();
					ServletFileUpload upload = new ServletFileUpload(factory);
					List<FileItem> items;
					items = upload.parseRequest(request);
					Iterator<FileItem> iterator = items.iterator();
					while (iterator.hasNext()) {
						// 获取表单中的所有元素
						FileItem item = (FileItem) iterator.next();
						// 普通表单元素
						if (item.isFormField()) {
							if ("fileds".equals(item.getFieldName())) {
			                    category = item.getString("UTF-8");  
							}
							if ("name".equals(item.getFieldName())) {
								name = item.getString("UTF-8");
								if (name == null || name.equals("")) {
									request.getSession().setAttribute("alert", "inputBookName");
									response.sendRedirect("alert.jsp");
									return;
//									request.getRequestDispatcher("alert.jsp").forward(request, response);
								}
								if (dao.checkName(name)) {
									request.getSession().setAttribute("alert", "bookNameExist");
									response.sendRedirect("alert.jsp");
									return;
//									request.getRequestDispatcher("alert.jsp").forward(request, response);
								}
							}
							if ("description".equals(item.getFieldName())) {
								description = item.getString("UTF-8");
							}
							if ("count".equals(item.getFieldName())) {
								if (item.getString() == null || item.getString().equals("")) {
									request.getSession().setAttribute("alert", "inputCount");
									response.sendRedirect("alert.jsp");
									return;
//									request.getRequestDispatcher("alert.jsp").forward(request, response);
								} else {
									try {
										count = Integer.parseInt(item
												.getString("UTF-8"));
										if (count <= 0) {
											request.getSession().setAttribute("alert", "inputCountError");
											response.sendRedirect("alert.jsp");
											return;
//											request.getRequestDispatcher("alert.jsp").forward(request, response);
										}
									} catch (Exception e) {
										request.getSession().setAttribute("alert", "inputCountError");
										response.sendRedirect("alert.jsp");
										return;
//										request.getRequestDispatcher("alert.jsp").forward(request, response);
									}
									
								}
							}
							
						}
						// 获取文件名，包括路径
						image = item.getName();
						if (image != null && !image.equals("")) {
							File fullFile = new File(item.getName());
							File saveFile = new File(uploadPath,
									fullFile.getName());
							// 对文件名进行截取，防止形如：C:\\aaa\bbb\ccc.jpg
							image = image
									.substring(image.lastIndexOf("\\") + 1);
							item.write(saveFile);
							// 把上传文件存放到目录中
						}
					}
					if (!dao.checkName(name) && name != null
							&& !name.equals("") && count != null) {
						book.setName(name);
						book.setDescription(description);
						if (image == null || image.equals("")) {
							book.setImg("default.jpg");
						} else {
							book.setImg(image);
						}
						book.setCategory(category);
						book.setCount(count);
						if (dao.add(book) && count != 0) {
							request.getSession().setAttribute("alert", "addBook");
							request.getRequestDispatcher("alert.jsp").forward(request, response);
						} else {
							request.getSession().setAttribute("alert", "addBookError");
							request.getRequestDispatcher("alert.jsp").forward(request, response);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else if ("deletebook".equals(action)) {
			Integer id = Integer.parseInt(request.getParameter("id"));
			if (dao.delete(id)) {
				request.getSession().setAttribute("alert", "deleteBook");
				request.getRequestDispatcher("alert.jsp").forward(request, response);
			} else {
				request.getSession().setAttribute("alert", "deleteBookError");
				request.getRequestDispatcher("alert.jsp").forward(request, response);
			}
		} else if ("listbook".equals(action)) {
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
			request.getRequestDispatcher("listbook.jsp").forward(request,
					response);
		} else if ("getOne".equals(action)) {
			Integer id = Integer.parseInt(request.getParameter("id"));
			BookEntity book = dao.getOne(id);
			request.setAttribute("book", book);
			request.getRequestDispatcher("updatebook.jsp").forward(request,
					response);
		} else if ("updatebook".equals(action)) {
			BookEntity book = new BookEntity();
			boolean isMultiPart = ServletFileUpload.isMultipartContent(request);
			String name = "";
			String description = "";
			String image = "";
			Integer id = null;
			Integer count = null;
			String category = "";
			if (isMultiPart) {
				try {
					FileItemFactory factory = new DiskFileItemFactory();
					ServletFileUpload upload = new ServletFileUpload(factory);
					List<FileItem> items;
					items = upload.parseRequest(request);
					Iterator<FileItem> iterator = items.iterator();
					while (iterator.hasNext()) {

						// 获取表单中的所有元素
						FileItem item = (FileItem) iterator.next();
						// 获取文件名，包括路径
						// 普通表单元素
						if (item.isFormField()) {
							if ("fileds".equals(item.getFieldName())) {
			                    category = item.getString("UTF-8");  
							}
							if ("id".equals(item.getFieldName())) {
								id = Integer.parseInt(item.getString("UTF-8"));
							}
							if ("name".equals(item.getFieldName())) {
								name = item.getString("UTF-8");
								if (name == null || name.equals("")) {
									request.getSession().setAttribute("alert", "updateBookName");
									request.getSession().setAttribute("bookId", id);
									response.sendRedirect("alert.jsp");
									return;
//									request.getRequestDispatcher("").forward(request, response);
								}
								if (!dao.getName(id, name)) {
									request.getSession().setAttribute("alert", "updateBookNameExist");
									request.getSession().setAttribute("bookId", id);
									response.sendRedirect("alert.jsp");
									return;
//									request.getRequestDispatcher("alert.jsp").forward(request, response);
								}
							}
							if ("description".equals(item.getFieldName())) {
								description = item.getString("UTF-8");
							}
							if ("count".equals(item.getFieldName())) {
								
								if (item.getString() == null || item.getString().equals("")) {
									request.getSession().setAttribute("alert", "updateBookCount");
									request.getSession().setAttribute("bookId", id);
									response.sendRedirect("alert.jsp");
									return;
//									request.getRequestDispatcher("alert.jsp").forward(request, response);
								} else {
									try {
										count = 0;
										count = Integer.parseInt(item
												.getString("UTF-8"));
										if (count <= 0) {
											request.getSession().setAttribute("alert", "updateBookCount");
											request.getSession().setAttribute("bookId", id);
											response.sendRedirect("alert.jsp");
											return;
//											request.getRequestDispatcher("alert.jsp").forward(request, response);
										}
									} catch (Exception e) {
										request.getSession().setAttribute("alert", "updateBookCount");
										request.getSession().setAttribute("bookId", id);
										response.sendRedirect("alert.jsp");
										return;
//										request.getRequestDispatcher("alert.jsp").forward(request, response);
									}
									
								}
							}
						}
						image = item.getName();
						if (image != null && !image.equals("")) {
							File fullFile = new File(item.getName());
							File saveFile = new File(uploadPath,
									fullFile.getName());
							// 对文件名进行截取，防止形如：C:\\aaa\bbb\ccc.jpg
							image = image
									.substring(image.lastIndexOf("\\") + 1);
							item.write(saveFile);
							// 把上传文件存放到目录中
						}
					}
					if (dao.getName(id, name) && name != null
							&& !name.equals("")) {
						book.setId(id);
						book.setCategory(category);
						book.setName(name);
						book.setDescription(description);
						book.setCount(count);

						if (image != null && !image.equals("")) {
							book.setImg(image);
						}
						if (dao.update(book) && count != 0) {
							request.getSession().setAttribute("alert", "updateBook");
//							response.sendRedirect("alert.jsp");
//							return;
							request.getRequestDispatcher("alert.jsp").forward(request, response);
						} else {
							request.getSession().setAttribute("alert", "updateBookError");
//							response.sendRedirect("alert.jsp");
//							return;
							request.getRequestDispatcher("alert.jsp").forward(request, response);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	} 

	@Override
	public void init() throws ServletException {
		uploadPath = new File(getServletContext().getRealPath("images/upload"));
		if (!uploadPath.exists()) {
		 uploadPath.mkdirs();
		}
	}
}