package cn.landis.dao.impl;

import java.sql.Connection;
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

import cn.landis.dao.BorrowDao;
import cn.landis.entity.BookRank;
import cn.landis.entity.BorrowEntity;
import cn.landis.util.JdbcUtil;

public class BorrowDaoImpl implements BorrowDao {

	/**
	 * 借书
	 */
	@Override
	public boolean addBorrow(BorrowEntity borrowEntity) {
		boolean flag = false;
		Connection connection = null;
		Statement statement = null;
		try {
			connection = JdbcUtil.getInstance().getConnection();
			statement = connection.createStatement();
			StringBuffer sb = new StringBuffer();
			sb.append("INSERT INTO borrow VALUES(NULL,")
					.append(borrowEntity.getStudentId()).append(",'")
					.append(borrowEntity.getStudentName()).append("',")
					.append(borrowEntity.getBookId()).append(",'")
					.append(borrowEntity.getBookName()).append("','")
					.append(borrowEntity.getStartDate()).append("'")
					.append(",'").append(borrowEntity.getEndDate()).append("',")
					.append(borrowEntity.getStudentStatus()).append(",'")
					.append(borrowEntity.getHasReturn()).append("',")
					.append(borrowEntity.getMoney())
					.append(")");
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
/**
 * 检查是否可以借阅
 */
	@Override
	public boolean checkBorrow(Integer studentId,Integer bookId) {
		boolean result = false;
		Connection connection = JdbcUtil.getInstance().getConnection();

		// 准备SQL语句
		String sql = "SELECT COUNT(*) FROM borrow WHERE studentId=" + studentId
				+ " AND bookId=" + bookId + " AND studentStatus=0";

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

	/**
	 * 获取学生的全部借阅历史
	 */
	@Override
	public List<BorrowEntity> getStudentBorrow(Map<String, String> condition,
			Integer pageNo, Integer pageSize,Integer studentId) {
		List<BorrowEntity> list = null;
		String filed = null;
		String content = null;
		Integer param = (pageNo - 1) * pageSize;
		StringBuffer sb = new StringBuffer();
		Connection connection = JdbcUtil.getInstance().getConnection();
		sb.append("SELECT * FROM borrow WHERE 1=1");
		for (Entry<String, String> temp : condition.entrySet()) {
			filed = temp.getKey();
			content = "%" + temp.getValue() + "%";
			if ("bookName".equals(filed)) {
				sb.append(" AND bookName LIKE ?");
			}
		}
		sb.append(" AND studentId=").append(studentId);
		sb.append(" LIMIT ?,?");
		QueryRunner queryRunner = new QueryRunner();
		try {
			if (filed != null && content != null) {
				list = queryRunner
						.query(connection, sb.toString(),
								new BeanListHandler<BorrowEntity>(
										BorrowEntity.class), content, param,
								pageSize);
			} else {
				list = queryRunner.query(connection, sb.toString(),
						new BeanListHandler<BorrowEntity>(
								BorrowEntity.class), param, pageSize);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(connection);
		}
		return list;
	}

	@Override
	public List<BorrowEntity> getNowBorrow(Integer studentId) {
		List<BorrowEntity> list = new ArrayList<BorrowEntity>();
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			connection = JdbcUtil.getInstance().getConnection();
			stmt = connection.createStatement();
			String sql = "SELECT * FROM borrow WHERE studentId=" + studentId + " AND studentStatus=0";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				BorrowEntity borrow = new BorrowEntity();
				borrow.setBookName(rs.getString("bookName"));
				borrow.setHasReturn(rs.getString("hasReturn"));
				borrow.setStartDate(rs.getString("startDate"));
				borrow.setEndDate(rs.getString("endDate"));
				list.add(borrow);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.getInstance().close(connection, stmt, rs);
		}
		return list;
	}

	@Override
	public List<BorrowEntity> getAllBorrow(Map<String, String> condition,
			Integer pageNo, Integer pageSize) {
		List<BorrowEntity> list = null;
		String filed = null;
		String content = null;
		Integer param = (pageNo - 1) * pageSize;
		StringBuffer sb = new StringBuffer();
		Connection connection = JdbcUtil.getInstance().getConnection();
		sb.append("SELECT * FROM borrow WHERE 1=1");
		for (Entry<String, String> temp : condition.entrySet()) {
			filed = temp.getKey();
			content = "%" + temp.getValue() + "%";
			if ("studentName".equals(filed)) {
				sb.append(" AND studentName LIKE ?");
			}
			if ("bookName".equals(filed)) {
				sb.append(" AND bookName LIKE ?");
			}
		}
		sb.append(" AND studentStatus=?");
		sb.append(" LIMIT ?,?");
		QueryRunner queryRunner = new QueryRunner();
		try {
			if (filed != null && content != null) {
				list = queryRunner
						.query(connection, sb.toString(),
								new BeanListHandler<BorrowEntity>(
										BorrowEntity.class), content, 0,param,
								pageSize);
			} else {
				list = queryRunner.query(connection, sb.toString(),
						new BeanListHandler<BorrowEntity>(
								BorrowEntity.class), 0,param, pageSize);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(connection);
		}
		return list;
	}

	@Override
	public boolean updateByReturn(BorrowEntity borrow) {
		boolean flag = false;
		Connection connection = null;
		Statement stmt = null;
		try {
			connection = JdbcUtil.getInstance().getConnection();
			stmt = connection.createStatement();
			StringBuffer sb = new StringBuffer();
			sb.append("UPDATE borrow SET endDate='").append(borrow.getEndDate()).append("',studentStatus=").append(borrow.getStudentStatus())
			.append(",hasReturn='").append(borrow.getHasReturn()).append("',money=").append(borrow.getMoney());
					sb.append(" WHERE studentId=").append(borrow.getStudentId()).append(" AND bookId=").append(borrow.getBookId());
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
	public  long getTotalCountByCondition(Map<String, String> condition) {
		// 获取连接
				Connection connection = JdbcUtil.getInstance().getConnection();

				// 准备SQL语句
				StringBuffer sb = new StringBuffer();
				sb.append("SELECT COUNT(*) FROM borrow WHERE 1 = 1 ");
				
				String key = null;
				String value = null;
				for (Entry<String, String> temp : condition.entrySet()) {
					key = temp.getKey();
					value = "%" + temp.getValue() + "%";
					if ("studentName".equals(key)) {
						sb.append(" AND studentName LIKE ?");
					} else if ("bookName".equals(key)) {
						sb.append(" AND bookName LIKE ?");
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
	public String getStartDate(Integer bookId, Integer studentId) {
		Connection connection = JdbcUtil.getInstance().getConnection();
		String sql = "SELECT startDate FROM borrow WHERE studentId=" + studentId + " AND bookId=" + bookId + " AND studentStatus=0";
		QueryRunner query = new QueryRunner();
		String result = null;
		try {
			result = query.query(connection, sql, new ScalarHandler<String>());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	@Override
	public List<BookRank> getBookRanks() {
		List<BookRank> list = new ArrayList<BookRank>();
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			connection = JdbcUtil.getInstance().getConnection();
			stmt = connection.createStatement();
			String sql = "SELECT bookName , COUNT(*) FROM borrow GROUP BY bookId";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				BookRank bookRank = new BookRank();
				bookRank.setBookName(rs.getString(1));
				bookRank.setCount(rs.getInt(2));
				list.add(bookRank);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
