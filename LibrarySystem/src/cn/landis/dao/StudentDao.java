package cn.landis.dao;

import java.util.List;
import java.util.Map;

import cn.landis.entity.StudentEntity;

public interface StudentDao {
	// 新增人员
	public abstract boolean add(StudentEntity student);
	// 列出所有人员
	public abstract List<StudentEntity> listAll();
	// 删除人员
	public abstract boolean delete(Integer id);
	// 读取一个人员
	public abstract StudentEntity getOne(Integer id);
	// 修改人员
	public abstract boolean update(StudentEntity student);
	// 按页查询
	public abstract List<StudentEntity> findByPage(Integer pageNo,Integer pageSize);
	// 获取总条数
	public abstract long getTotalCountByCondition(Map<String, String> condition);
	// 按条件查询
	public abstract List<StudentEntity> findByCondition(Map<String, String> condition,Integer pageNo,Integer pageSize);
	// 查询所有的名字
	public abstract boolean checkName(String username);
	// 获取单个名字
	public abstract boolean getName(Integer id,String username);
}
