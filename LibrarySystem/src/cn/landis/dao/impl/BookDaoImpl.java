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

import cn.landis.dao.BookDao;
import cn.landis.entity.BookEntity;
import cn.landis.util.JdbcUtil;

public class BookDaoImpl implements BookDao {

	@Override
	public boolean add(BookEntity book) {
		boolean flag = false;
		Connection connection = null;
		Statement statement = null;
		try {
			connection = JdbcUtil.getInstance().getConnection();
			statement = connection.createStatement();
			StringBuffer sb = new StringBuffer();
			sb.append("INSERT INTO book VALUES(NULL,'")
					.append(book.getImg()).append("','")
					.append(book.getCategory()).append("','")
					.append(book.getName()).append("'");
			if (book.getDescription() == null
					|| book.getDescription().equals("")) {
				sb.append(",").append("DEFAULT");
			} else {
				sb.append(",'").append(book.getDescription()).append("'");
			}
			sb.append(",").append(book.getCount()).append(")");
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
	public List<BookEntity> listAll() {
		List<BookEntity> list = new ArrayList<BookEntity>();
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			connection = JdbcUtil.getInstance().getConnection();
			stmt = connection.createStatement();
			String sql = "SELECT * FROM book";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				BookEntity book = new BookEntity();
				book.setImg(rs.getString("img"));
				book.setCategory(rs.getString("category"));
				book.setName(rs.getString("name"));
				book.setDescription(rs.getString("description"));
				book.setCount(rs.getInt("count"));
				list.add(book);
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
			String sql = "DELETE FROM book WHERE id=" + id;
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
	public BookEntity getOne(Integer id) {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		BookEntity book = new BookEntity();
		try {
			connection = JdbcUtil.getInstance().getConnection();
			stmt = connection.createStatement();
			String sql = "SELECT * FROM book WHERE id=" + id;
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				book.setId(rs.getInt("id"));
				book.setImg(rs.getString("img"));
				book.setCategory(rs.getString("category"));
				book.setName(rs.getString("name"));
				book.setDescription(rs.getString("description"));
				book.setCount(rs.getInt("count"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.getInstance().close(connection, stmt, rs);
		}
		return book;
	}

	@Override
	public boolean update(BookEntity book) {
		boolean flag = false;
		Connection connection = null;
		Statement stmt = null;
		try {
			connection = JdbcUtil.getInstance().getConnection();
			stmt = connection.createStatement();
			StringBuffer sb = new StringBuffer();
			sb.append("UPDATE book SET name='").append(
					book.getName()).append("',category='").append(book.getCategory());
			if (book.getImg() != null && !book.getImg().equals("")) {
				sb.append("',img='").append(book.getImg());
			}
			if (book.getDescription() == null || book.getDescription().equals("")) {
				sb.append("',description=").append("DEFAULT");
			} else {
			sb.append("',description='").append(book.getDescription()).append("'");
			}
			sb.append(",count=").append(book.getCount());
					sb.append(" WHERE id=").append(book.getId());
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
	public List<BookEntity> findByPage(Integer pageNo, Integer pageSize) {
		List<BookEntity> list = new ArrayList<BookEntity>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = JdbcUtil.getInstance().getConnection();
			String sql = "SELECT * FROM book LIMIT ? , ?";
			Integer param = (pageNo - 1) * pageSize;
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, param);
			pstmt.setInt(2, pageSize);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BookEntity book = new BookEntity();
				book.setId(rs.getInt("id"));
				book.setCategory(rs.getString("category"));
				book.setName(rs.getString("name"));
				book.setDescription(rs.getString("description"));
				book.setCount(rs.getInt("count"));
				list.add(book);
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
				sb.append("SELECT COUNT(*) FROM book WHERE 1 = 1 ");
				
				String key = null;
				String value = null;
				for (Entry<String, String> temp : condition.entrySet()) {
					key = temp.getKey();
					value = "%" + temp.getValue() + "%";
					if ("category".equals(key)) {
						sb.append(" AND category LIKE ?");
					} else if ("name".equals(key)) {
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

	@Override
	public List<BookEntity> findByCondition(Map<String, String> condition,
			Integer pageNo, Integer pageSize) {
		List<BookEntity> list = null;
		String filed = null;
		String content = null;
		Integer param = (pageNo - 1) * pageSize;
		StringBuffer sb = new StringBuffer();
		Connection connection = JdbcUtil.getInstance().getConnection();
		sb.append("SELECT * FROM book WHERE 1=1");
		for (Entry<String, String> temp : condition.entrySet()) {
			filed = temp.getKey();
			content = "%" + temp.getValue() + "%";
			if ("name".equals(filed)) {
				sb.append(" AND name LIKE ?");
			}
			if ("category".equals(filed)) {
				sb.append(" AND category LIKE ?");
			}
		}
		sb.append(" LIMIT ?,?");
		QueryRunner queryRunner = new QueryRunner();
		try {
			if (filed != null && content != null) {
				list = queryRunner
						.query(connection, sb.toString(),
								new BeanListHandler<BookEntity>(
										BookEntity.class), content, param,
								pageSize);
			} else {
				list = queryRunner.query(connection, sb.toString(),
						new BeanListHandler<BookEntity>(
								BookEntity.class), param, pageSize);
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
			String sql = "SELECT name FROM book";
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
		String sql = "SELECT COUNT(name) FROM book WHERE id<>" + id
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
	public List<String> getCategoryList() {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<String> list = new ArrayList<String>();
		try {
			connection = JdbcUtil.getInstance().getConnection();
			stmt = connection.createStatement();
			String sql = "SELECT name FROM category";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String name = rs.getString("name");
				list.add(name);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.getInstance().close(connection, stmt, rs);
		}
		return list;
	}

	@Override
	public boolean updateByBorrow(int bookId,int count) {
		boolean flag = false;
		Connection connection = null;
		Statement stmt = null;
		try {
			connection = JdbcUtil.getInstance().getConnection();
			stmt = connection.createStatement();
			StringBuffer sb = new StringBuffer();
			sb.append("UPDATE book SET count=").append(count);
					sb.append(" WHERE id=").append(bookId);
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
	public long getBorrowCount(Integer bookId) {
		Connection connection = JdbcUtil.getInstance().getConnection();

		// 准备SQL语句
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT COUNT(*) FROM borrow WHERE bookId=").append(bookId);
		
		// 创建QueryRunner类的实例
		QueryRunner queryRunner = new QueryRunner();

		Long result = null;

		try {
				result = queryRunner.query(connection, sb.toString(), new ScalarHandler<Long>());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// 关闭
		DbUtils.closeQuietly(connection);

		return result;
	}

}