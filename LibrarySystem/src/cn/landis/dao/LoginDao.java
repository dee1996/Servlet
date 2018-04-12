package cn.landis.dao;

import cn.landis.entity.AdminEntity;
import cn.landis.entity.StudentEntity;


public interface LoginDao {
	public abstract AdminEntity adminLogin(AdminEntity admin);
	public abstract StudentEntity stuLogin(StudentEntity student);
}