package cn.landis.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.landis.dao.StudentDao;
import cn.landis.entity.StudentEntity;
import cn.landis.util.JdbcUtil;

public class StudentDaoImpl implements StudentDao {

	@Override
	public boolean add(StudentEntity student) {
		boolean flag = false;
		Connection connection = null;
		Statement statement = null;
		try {
			connection = JdbcUtil.getInstance().getConnection();
			statement = connection.createStatement();
			StringBuffer sb = new StringBuffer();
			sb.append("INSERT INTO student VALUES(NULL,'")
					.append(student.getImg()).append("','")
					.append(student.getUsername()).append("','")
					.append(student.getPassword()).append("')");
			int result = statement.executeUpdate(sb.toString());
			if (result > 0) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.getInstance().close(connection, statement, null);
		}
		return flag;
	}

	@Override
	public List<StudentEntity> listAll() {
		List<StudentEntity> list = new ArrayList<StudentEntity>();
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			connection = JdbcUtil.getInstance().getConnection();
			stmt = connection.createStatement();
			String sql = "SELECT * FROM student";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				StudentEntity studentEntity = new StudentEntity();
				studentEntity.setImg(rs.getString("img"));
				studentEntity.setUsername(rs.getString("username"));
				studentEntity.setPassword(rs.getString("password"));
				list.add(studentEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.getInstance().close(connection, stmt, rs);
		}
		return list;
	}

	@Override
	public boolean delete(Integer id) {
		boolean flag = false;
		Connection connection = null;
		Statement stmt = null;
		try {
			connection = JdbcUtil.getInstance().getConnection();
			stmt = connection.createStatement();
			String sql = "DELETE FROM student WHERE id=" + id;
			int result = stmt.executeUpdate(sql);
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

	@Override
	public StudentEntity getOne(Integer id) {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		StudentEntity student = new StudentEntity();
		try {
			connection = JdbcUtil.getInstance().getConnection();
			stmt = connection.createStatement();
			String sql = "SELECT * FROM student WHERE id=" + id;
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				student.setId(rs.getInt("id"));
				student.setImg(rs.getString("img"));
				student.setUsername(rs.getString("username"));
				student.setPassword(rs.getString("password"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.getInstance().close(connection, stmt, rs);
		}
		return student;
	}

	@Override
	public boolean update(StudentEntity student) {
		boolean flag = false;
		Connection connection = null;
		Statement stmt = null;
		try {
			connection = JdbcUtil.getInstance().getConnection();
			stmt = connection.createStatement();
			StringBuffer sb = new StringBuffer();
			sb.append("UPDATE student SET username='").append(
					student.getUsername());
			if (student.getImg() != null && !student.getImg().equals("")) {
				sb.append("',img='").append(student.getImg());
			}
			sb.append("',password='").append(student.getPassword())
					.append("' WHERE id=").append(student.getId());
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

	@Override
	public List<StudentEntity> findByPage(Integer pageNo, Integer pageSize) {
		List<StudentEntity> list = new ArrayList<StudentEntity>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = JdbcUtil.getInstance().getConnection();
			String sql = "SELECT * FROM student LIMIT ? , ?";
			Integer param = (pageNo - 1) * pageSize;
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, param);
			pstmt.setInt(2, pageSize);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				StudentEntity student = new StudentEntity();
				student.setId(rs.getInt("id"));
				student.setUsername(rs.getString("username"));
				student.setPassword(rs.getString("password"));
				list.add(student);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.getInstance().close(connection, pstmt, rs);
		}
		return list;
	}

	@Override
	public  long getTotalCountByCondition(Map<String, String> condition) {
		// 获取连接
				Connection connection = JdbcUtil.getInstance().getConnection();

				// 准备SQL语句
				StringBuffer sb = new StringBuffer();
				sb.append("SELECT COUNT(*) FROM student WHERE 1 = 1 ");
				
				String key = null;
				String value = null;
				for (Entry<String, String> temp : condition.entrySet()) {
					key = temp.getKey();
					value = "%" + temp.getValue() + "%";
					if ("username".equals(key)) {
						sb.append(" AND username LIKE ?");
					} 
				}
				
				// 创建QueryRunner类的实例
				QueryRunner queryRunner = new QueryRunner();

				Long result = null;

				try {
					if (key != null && value != null) {
						result = queryRunner.query(connection, sb.toString(), new ScalarHandler<Long>(), value);
					} else {
						result = queryRunner.query(connection, sb.toString(), new ScalarHandler<Long>());
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

				// 关闭
				DbUtils.closeQuietly(connection);

				return result;
	}

	@Override
	public List<StudentEntity> findByCondition(Map<String, String> condition,
			Integer pageNo, Integer pageSize) {
		List<StudentEntity> list = null;
		String filed = null;
		String content = null;
		Integer param = (pageNo - 1) * pageSize;
		StringBuffer sb = new StringBuffer();
		Connection connection = JdbcUtil.getInstance().getConnection();
		sb.append("SELECT * FROM student WHERE 1=1");
		for (Entry<String, String> temp : condition.entrySet()) {
			filed = temp.getKey();
			content = "%" + temp.getValue() + "%";
			if ("username".equals(filed)) {
				sb.append(" AND username LIKE ?");
			}
		}
		sb.append(" LIMIT ?,?");
		QueryRunner queryRunner = new QueryRunner();
		try {
			if (filed != null && content != null) {
				list = queryRunner
						.query(connection, sb.toString(),
								new BeanListHandler<StudentEntity>(
										StudentEntity.class), content, param,
								pageSize);
			} else {
				list = queryRunner
						.query(connection, sb.toString(),
								new BeanListHandler<StudentEntity>(
										StudentEntity.class), param, pageSize);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(connection);
		}
		return list;
	}

	@Override
	public boolean checkName(String username) {
		boolean result = false;
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			connection = JdbcUtil.getInstance().getConnection();
			stmt = connection.createStatement();
			String sql = "SELECT username FROM student";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String sqlusername = rs.getString("username");
				if (username.equals(sqlusername)) {
					result = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.getInstance().close(connection, stmt, rs);
		}
		return result;
	}

	@Override
	public boolean getName(Integer id,String username) {
		boolean result = false;
		Connection connection = JdbcUtil.getInstance().getConnection();

		// 准备SQL语句
		String sql = "SELECT COUNT(username) FROM student WHERE id<>" + id + " AND username='" + username + "'";

		// 创建QueryRunner类的实例
		QueryRunner queryRunner = new QueryRunner();

		Long count = null;

		try {
			count = queryRunner.query(connection, sql,
					new ScalarHandler<Long>());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (count == 0) {
			result = true;
		}
		// 关闭
		DbUtils.closeQuietly(connection);

		return result;
	}
}