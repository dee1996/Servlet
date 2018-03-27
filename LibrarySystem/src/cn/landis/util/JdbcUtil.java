package cn.landis.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JdbcUtil {
	private static String driverName;
	private static String url;
	private static String username;
	private static String password;
	private static Properties properties = new Properties();
	private static JdbcUtil jdbcUtil;
	private JdbcUtil() {
		
	}
	public static JdbcUtil getInstance() {
		if (jdbcUtil == null) {
			jdbcUtil = new JdbcUtil();
		}
		return jdbcUtil;
	}
	static {
		try {
			InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("dbconfig.properties");
			properties.load(in);
			driverName = properties.getProperty("driverName");
			url = properties.getProperty("url");
			username = properties.getProperty("username");
			password = properties.getProperty("password");
			Class.forName(driverName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Connection getConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	public void close(Connection connection,Statement statement,ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (statement != null) {
			try {
				statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}