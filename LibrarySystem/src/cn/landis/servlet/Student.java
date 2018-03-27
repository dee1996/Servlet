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

import cn.landis.dao.StudentDao;
import cn.landis.dao.impl.StudentDaoImpl;
import cn.landis.entity.StudentEntity;
import cn.landis.model.PageModel;

public class Student extends HttpServlet {
	public File uploadPath = null;

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uri = request.getRequestURI();
		StudentDao dao = new StudentDaoImpl();
		String action = uri.substring(uri.lastIndexOf("/") + 1,
				uri.lastIndexOf("."));
		if ("studentadd".equals(action) || "register".equals(action)) {
			StudentEntity student = new StudentEntity();
			boolean isMultiPart = ServletFileUpload.isMultipartContent(request);
			String username = "";
			String password = "";
			String image = "";
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
							if ("username".equals(item.getFieldName())) {
								username = item.getString("UTF-8");
								if (username == null || username.equals("")) {
									request.getSession().setAttribute("alert", "inputUsername");
									response.sendRedirect("alert.jsp");
									return;
//									out.print("请填入账号");
								}
								if (dao.checkName(username)) {
									request.getSession().setAttribute("alert", "usernameExist");
									response.sendRedirect("alert.jsp");
									return;
//									out.print("该账号已存在!");
								}
							}
							if ("password".equals(item.getFieldName())) {
								password = item.getString("UTF-8");
								if (password == null || password.equals("")) {
									request.getSession().setAttribute("alert", "inputPassword");
									response.sendRedirect("alert.jsp");
									return;
//									out.print("请输入密码");
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
					if (!dao.checkName(username) && username != null
							&& password != null && !username.equals("")
							&& !password.equals("")) {
						student.setUsername(username);
						student.setPassword(password);
						student.setImg(image);
						if (dao.add(student)) {
							if ("studentadd".equals(action)) {
								request.getSession().setAttribute("alert", "addStudent");
								request.getRequestDispatcher("alert.jsp").forward(request, response);
							} else {
								request.getSession().setAttribute("alert", "register");
								request.getRequestDispatcher("alert.jsp").forward(request, response);
							}
						} else {
							if ("studentadd".equals(action)) {
								request.getSession().setAttribute("alert", "addStudentError");
								request.getRequestDispatcher("alert.jsp").forward(request, response);
							} else {
								request.getSession().setAttribute("alert", "registerError");
								request.getRequestDispatcher("alert.jsp").forward(request, response);
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else if ("deletestudent".equals(action)) {
			Integer id = Integer.parseInt(request.getParameter("id"));
			if (dao.delete(id)) {
				request.getSession().setAttribute("alert", "deleteStudent");
				request.getRequestDispatcher("alert.jsp").forward(request, response);
			} else {
				request.getSession().setAttribute("alert", "deleteStudentError");
				request.getRequestDispatcher("alert.jsp").forward(request, response);
			}
		} else if ("liststudent".equals(action)) {
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
			List<StudentEntity> list = dao
					.findByCondition(condition, pageNo, 3);
			pageModel.setList(list);
			pageModel.setPageNo(pageNo);
			pageModel.setPageSize(3);
			pageModel.setTotalCount((int) dao.getTotalCountByCondition(condition));
			request.getSession().setAttribute("filed", filed);
			request.getSession().setAttribute("content", content);
			request.setAttribute("pageModel", pageModel);
			request.getRequestDispatcher("liststudent.jsp").forward(request,
					response);
		} else if ("getOne".equals(action) || ("getOneByStudent".equals(action))) {
			Integer id = Integer.parseInt(request.getParameter("id"));
			StudentEntity student = dao.getOne(id);
			request.setAttribute("student", student);
			if ("getOne".equals(action)) {
				request.getRequestDispatcher("updatestudent.jsp").forward(request,
						response);
			} else {
				request.getRequestDispatcher("updatestudentByStudent.jsp").forward(request,
						response);
			}
		} else if ("updatestudent".equals(action) || "updatestudentByStudent".equals(action)) {
			StudentEntity student = new StudentEntity();
			boolean isMultiPart = ServletFileUpload.isMultipartContent(request);
			String username = "";
			String password = "";
			String image = "";
			Integer id = null;
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
							if ("id".equals(item.getFieldName())) {
								id = Integer.parseInt(item.getString("UTF-8"));
							}
							if ("username".equals(item.getFieldName())) {
								username = item.getString("UTF-8");
								if (username == null || username.equals("")) {
									request.getSession().setAttribute("username", id);
									if ("updatestudent".equals(action)) {
										request.getSession().setAttribute("alert", "updateInputUsername");
										response.sendRedirect("alert.jsp");
										return;
									} else {
										request.getSession().setAttribute("alert", "updateInputUsernameByStudent");
										response.sendRedirect("alert.jsp");
										return;
									}
//									out.print("请填入账号");
								}
								if (!dao.getName(id, username)) {
									request.getSession().setAttribute("username", id);
									if ("updatestudent".equals(action)) {
										request.getSession().setAttribute("alert", "updateUsernameExist");
										response.sendRedirect("alert.jsp");
										return;
									} else {
										request.getSession().setAttribute("alert", "updateUsernameExistByStudent");
										response.sendRedirect("alert.jsp");
										return;
									}
//									out.print("该账号已存在!");
								}
							}
							if ("password".equals(item.getFieldName())) {
								password = item.getString("UTF-8");
								if (password == null || password.equals("")) {
									request.getSession().setAttribute("username", id);
									if ("updatestudent".equals(action)) {
										request.getSession().setAttribute("alert", "updatePassword");
										response.sendRedirect("alert.jsp");
									} else {
										request.getSession().setAttribute("alert", "updatePasswordByStudent");
										response.sendRedirect("alert.jsp");
									}
//									out.print("请输入密码");
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
					if (dao.getName(id, username) && username != null
							&& password != null && !username.equals("")
							&& !password.equals("")) {
						student.setId(id);
						student.setUsername(username);
						student.setPassword(password);
						if (image != null && !image.equals("")) {
							student.setImg(image);
						}
						if (dao.update(student)) {
							if ("updatestudent".equals(action)) {
								request.getSession().setAttribute("alert", "updateStudent");
								request.getRequestDispatcher("alert.jsp").forward(request, response);	
							} else {
								request.getSession().setAttribute("student", student);
								request.getSession().setAttribute("alert", "updateStudentByStudent");
								request.getRequestDispatcher("alert.jsp").forward(request, response);	
							}
						} else {
							if ("updatestudent".equals(action)) {
								request.getSession().setAttribute("alert", "updateStudentError");
								request.getRequestDispatcher("alert.jsp").forward(request, response);	
							} else {
								request.getSession().setAttribute("student", student);
								request.getSession().setAttribute("alert", "updateStudentByStudentError");
								request.getRequestDispatcher("alert.jsp").forward(request, response);	
							}
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