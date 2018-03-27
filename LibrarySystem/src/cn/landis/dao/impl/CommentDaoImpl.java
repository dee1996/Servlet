package cn.landis.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cn.landis.dao.CommentDao;
import cn.landis.entity.CommentEntity;
import cn.landis.util.JdbcUtil;

public class CommentDaoImpl implements CommentDao {
	/**
	 * 获取全部评论
	 */

	@Override
	public List<CommentEntity> getAllComment() {
		List<CommentEntity> list = new ArrayList<>();
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			connection = JdbcUtil.getInstance().getConnection();
			stmt = connection.createStatement();
			String sql = "SELECT * FROM comment";
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				CommentEntity comment = new CommentEntity();
				comment.setId(rs.getInt("id"));
				comment.setStudentId(rs.getInt("studentId"));
				comment.setStudentComment(rs.getString("studentComment"));
				comment.setCommentDate(rs.getString("commentDate"));
				list.add(comment);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.getInstance().close(connection, stmt, rs);
		}
		return list;
	}

	@Override
	public boolean addComment(CommentEntity comment) {
		boolean flag = false;
		Connection connection = null;
		Statement stmt = null;
		try {
			connection = JdbcUtil.getInstance().getConnection();
			stmt = connection.createStatement();
			StringBuffer sb = new StringBuffer();
			sb.append("INSERT INTO comment VALUES(NULL,")
			.append(comment.getStudentId())
			.append(",'").append(comment.getStudentComment())
			.append("','").append(comment.getCommentDate())
			.append("')");
			stmt = connection.createStatement();
			int result = stmt.executeUpdate(sb.toString());
			if (result > 0) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.getInstance().close(connection, stmt, null);
		}
		return flag;
	}
		
}