package cn.landis.dao;

import java.util.List;
import java.util.Map;

import cn.landis.entity.CategoryEntity;


public interface CategoryDao {
	// 获取总条数
	public abstract long getTotalCountByCondition(Map<String, String> condition);
	// 新增图书
	public abstract boolean add(CategoryEntity category);
	// 列出所有图书
	public abstract List<CategoryEntity> listAll();
	// 删除图书
	public abstract boolean delete(Integer id);
	// 读取一本图书
	public abstract CategoryEntity getOne(Integer id);
	// 修改图书
	public abstract boolean update(CategoryEntity category);
	// 按页查询
	public abstract List<CategoryEntity> findByPage(Integer pageNo,Integer pageSize);
	// 按条件查询
	public abstract List<CategoryEntity> findByCondition(Map<String, String> condition,Integer pageNo,Integer pageSize);
	// 查询所有的名字
	public abstract boolean checkName(String name);
	// 获取单个名字
	public abstract boolean getName(Integer id,String name);
}
