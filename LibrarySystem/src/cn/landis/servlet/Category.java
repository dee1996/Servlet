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

import cn.landis.dao.CategoryDao;
import cn.landis.dao.impl.CategoryDaoImpl;
import cn.landis.entity.CategoryEntity;
import cn.landis.model.PageModel;

public class Category extends HttpServlet {
	public File uploadPath = null;

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uri = request.getRequestURI();
		CategoryDao dao = new CategoryDaoImpl();
		String action = uri.substring(uri.lastIndexOf("/") + 1,
				uri.lastIndexOf("."));
		if ("categoryadd".equals(action)) {
			CategoryEntity category = new CategoryEntity();
			boolean isMultiPart = ServletFileUpload.isMultipartContent(request);
			String name = "";
			String description = "";
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
							if ("name".equals(item.getFieldName())) {
								name = item.getString("UTF-8");
								if (name == null || name.equals("")) {
									request.getSession().setAttribute("alert", "inputCategory");
									response.sendRedirect("alert.jsp");
									return;
								}
								if (dao.checkName(name)) {
									request.getSession().setAttribute("alert", "categoryExist");
									response.sendRedirect("alert.jsp");
									return;
								}
							}
							if ("description".equals(item.getFieldName())) {
								description = item.getString("UTF-8");
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
							&& !name.equals("")) {
						category.setName(name);
						category.setDescription(description);
						category.setImg(image);
						if (dao.add(category)) {
							request.getSession().setAttribute("alert", "addCategory");
							request.getRequestDispatcher("alert.jsp").forward(request, response);
						} else {
							request.getSession().setAttribute("alert", "addCategoryError");
							request.getRequestDispatcher("alert.jsp").forward(request, response);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else if ("deletecategory".equals(action)) {
			Integer id = Integer.parseInt(request.getParameter("id"));
			if (dao.delete(id)) {
				request.getSession().setAttribute("alert", "deleteCategory");
				request.getRequestDispatcher("alert.jsp").forward(request, response);
			} else {
				request.getSession().setAttribute("alert", "deleteCategoryError");
				request.getRequestDispatcher("alert.jsp").forward(request, response);
			}
		} else if ("listcategory".equals(action)) {
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
			List<CategoryEntity> list = dao.findByCondition(condition, pageNo,
					3);
			pageModel.setList(list);
			pageModel.setPageNo(pageNo);
			pageModel.setPageSize(3);
			pageModel.setTotalCount((int) dao.getTotalCountByCondition(condition));
			request.getSession().setAttribute("filed", filed);
			request.getSession().setAttribute("content", content);
			request.setAttribute("pageModel", pageModel);
			request.getRequestDispatcher("listcategory.jsp").forward(request,
					response);
		} else if ("getOne".equals(action)) {
			Integer id = Integer.parseInt(request.getParameter("id"));
			CategoryEntity category = dao.getOne(id);
			request.setAttribute("category", category);
			request.getRequestDispatcher("updatecategory.jsp").forward(request,
					response);
		} else if ("updatecategory".equals(action)) {
			CategoryEntity category = new CategoryEntity();
			boolean isMultiPart = ServletFileUpload.isMultipartContent(request);
			String name = "";
			String description = "";
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
							if ("name".equals(item.getFieldName())) {
								name = item.getString("UTF-8");
								if (name == null || name.equals("")) {
									request.getSession().setAttribute("categoryId", id);
									request.getSession().setAttribute("alert", "updateInputCategory");
									response.sendRedirect("alert.jsp");
									return;
								}
								if (!dao.getName(id, name)) {
									request.getSession().setAttribute("categoryId", id);
									request.getSession().setAttribute("alert", "updateCategoryExist");
									response.sendRedirect("alert.jsp");
									return;
								}
							}
							if ("description".equals(item.getFieldName())) {
								description = item.getString("UTF-8");
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
						category.setId(id);
						category.setName(name);
						category.setDescription(description);

						if (image != null && !image.equals("")) {
							category.setImg(image);
						}
						if (dao.update(category)) {
							request.getSession().setAttribute("alert", "updateCategory");
							request.getRequestDispatcher("alert.jsp").forward(request, response);
						} else {
							request.getSession().setAttribute("alert", "updateCategoryError");
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