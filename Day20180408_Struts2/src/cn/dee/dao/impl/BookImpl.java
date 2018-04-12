package cn.dee.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import cn.dee.dao.BookDao;
import cn.dee.action.BookAction;
import cn.dee.util.JdbcUtil;

public class BookImpl implements BookDao {

	@Override
	public BookAction getBook(String name) {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		BookAction result = new BookAction();
		try {
			connection = JdbcUtil.getInstance().getConnection();
			statement = connection.createStatement();
			StringBuffer sb = new StringBuffer();
			sb.append("select * from book where name like '%").append(name).append("%'");
			System.out.println(sb.toString());
			resultSet = statement.executeQuery(sb.toString());
			while(resultSet.next()) {
				result.setId(resultSet.getInt("id"));
				result.setCategroy(resultSet.getString("category"));
				result.setCount(resultSet.getInt("count"));
				result.setName(resultSet.getString("name"));
			}
		} catch (Exception e) {
		}
		return result;
	}

}