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

import cn.landis.dao.CategoryDao;
import cn.landis.entity.CategoryEntity;
import cn.landis.util.JdbcUtil;

public class CategoryDaoImpl implements CategoryDao {

	@Override
	public boolean add(CategoryEntity category) {
		boolean flag = false;
		Connection connection = null;
		Statement statement = null;
		try {
			connection = JdbcUtil.getInstance().getConnection();
			statement = connection.createStatement();
			StringBuffer sb = new StringBuffer();
			sb.append("INSERT INTO category VALUES(NULL,'")
					.append(category.getImg()).append("','")
					.append(category.getName()).append("'");
			if (category.getDescription() == null
					|| category.getDescription().equals("")) {
				sb.append(",").append("DEFAULT)");
			} else {
				sb.append(",'").append(category.getDescription()).append("')");
			}
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
	public List<CategoryEntity> listAll() {
		List<CategoryEntity> list = new ArrayList<CategoryEntity>();
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			connection = JdbcUtil.getInstance().getConnection();
			stmt = connection.createStatement();
			String sql = "SELECT * FROM category";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				CategoryEntity category = new CategoryEntity();
				category.setImg(rs.getString("img"));
				category.setName(rs.getString("name"));
				category.setDescription(rs.getString("description"));
				list.add(category);
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
			String sql = "DELETE FROM category WHERE id=" + id;
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
	public CategoryEntity getOne(Integer id) {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		CategoryEntity category = new CategoryEntity();
		try {
			connection = JdbcUtil.getInstance().getConnection();
			stmt = connection.createStatement();
			String sql = "SELECT * FROM category WHERE id=" + id;
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				category.setId(rs.getInt("id"));
				category.setImg(rs.getString("img"));
				category.setName(rs.getString("name"));
				category.setDescription(rs.getString("description"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.getInstance().close(connection, stmt, rs);
		}
		return category;
	}

	@Override
	public boolean update(CategoryEntity category) {
		boolean flag = false;
		Connection connection = null;
		Statement stmt = null;
		try {
			connection = JdbcUtil.getInstance().getConnection();
			stmt = connection.createStatement();
			StringBuffer sb = new StringBuffer();
			sb.append("UPDATE category SET name='").append(
					category.getName());
			if (category.getImg() != null && !category.getImg().equals("")) {
				sb.append("',img='").append(category.getImg());
			}
			if (category.getDescription() == null || category.getDescription().equals("")) {
				sb.append("',description=").append("DEFAULT");
			} else {
			sb.append("',description='").append(category.getDescription()).append("'");
			}
					sb.append(" WHERE id=").append(category.getId());
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
	public List<CategoryEntity> findByPage(Integer pageNo, Integer pageSize) {
		List<CategoryEntity> list = new ArrayList<CategoryEntity>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = JdbcUtil.getInstance().getConnection();
			String sql = "SELECT * FROM category LIMIT ? , ?";
			Integer param = (pageNo - 1) * pageSize;
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, param);
			pstmt.setInt(2, pageSize);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CategoryEntity category = new CategoryEntity();
				category.setId(rs.getInt("id"));
				category.setName(rs.getString("name"));
				category.setDescription(rs.getString("description"));
				list.add(category);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.getInstance().close(connection, pstmt, rs);
		}
		return list;
	}


	@Override
	public List<CategoryEntity> findByCondition(Map<String, String> condition,
			Integer pageNo, Integer pageSize) {
		List<CategoryEntity> list = null;
		String filed = null;
		String content = null;
		Integer param = (pageNo - 1) * pageSize;
		StringBuffer sb = new StringBuffer();
		Connection connection = JdbcUtil.getInstance().getConnection();
		sb.append("SELECT * FROM category WHERE 1=1");
		for (Entry<String, String> temp : condition.entrySet()) {
			filed = temp.getKey();
			content = "%" + temp.getValue() + "%";
			if ("name".equals(filed)) {
				sb.append(" AND name LIKE ?");
			}
		}
		sb.append(" LIMIT ?,?");
		QueryRunner queryRunner = new QueryRunner();
		try {
			if (filed != null && content != null) {
				list = queryRunner
						.query(connection, sb.toString(),
								new BeanListHandler<CategoryEntity>(
										CategoryEntity.class), content, param,
								pageSize);
			} else {
				list = queryRunner.query(connection, sb.toString(),
						new BeanListHandler<CategoryEntity>(
								CategoryEntity.class), param, pageSize);
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
			String sql = "SELECT name FROM category";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String sqlusername = rs.getString("name");
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
	public boolean getName(Integer id, String name) {
		boolean result = false;
		Connection connection = JdbcUtil.getInstance().getConnection();

		// 准备SQL语句
		String sql = "SELECT COUNT(name) FROM category WHERE id<>" + id
				+ " AND name='" + name + "'";

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
	@Override
	public  long getTotalCountByCondition(Map<String, String> condition) {
		// 获取连接
				Connection connection = JdbcUtil.getInstance().getConnection();

				// 准备SQL语句
				StringBuffer sb = new StringBuffer();
				sb.append("SELECT COUNT(*) FROM category WHERE 1 = 1 ");
				
				String key = null;
				String value = null;
				for (Entry<String, String> temp : condition.entrySet()) {
					key = temp.getKey();
					value = "%" + temp.getValue() + "%";
					if ("name".equals(key)) {
						sb.append(" AND name LIKE ?");
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
}