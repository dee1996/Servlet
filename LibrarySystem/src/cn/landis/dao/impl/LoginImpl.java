package cn.landis.dao.impl;

import java.sql.Connection;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.landis.dao.LoginDao;
import cn.landis.entity.AdminEntity;
import cn.landis.entity.StudentEntity;
import cn.landis.util.JdbcUtil;


public class LoginImpl implements LoginDao {


	@Override
	public AdminEntity adminLogin(AdminEntity admin) {
		AdminEntity adminEntity = null;
		Connection connection = JdbcUtil.getInstance().getConnection();
		String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";
		QueryRunner queryRunner = new QueryRunner();
		try {
			adminEntity = queryRunner.query(connection, sql, new BeanHandler<AdminEntity>(AdminEntity.class),admin.getUsername(),admin.getPassword());
		} catch (Exception e) {
			e.printStackTrace();
		}
		DbUtils.closeQuietly(connection);
		return adminEntity;
	}

	@Override
	public StudentEntity stuLogin(StudentEntity student) {
		StudentEntity studentEntity = null;
		Connection connection = JdbcUtil.getInstance().getConnection();
		String sql = "SELECT * FROM student WHERE username = ? AND password = ?";
		QueryRunner queryRunner = new QueryRunner();
		try {
			studentEntity = queryRunner.query(connection, sql, new BeanHandler<StudentEntity>(StudentEntity.class),student.getUsername(),student.getPassword());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		DbUtils.closeQuietly(connection);
		return studentEntity;
	}

}