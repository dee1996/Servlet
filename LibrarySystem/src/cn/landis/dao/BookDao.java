package cn.landis.dao;

import java.util.List;
import java.util.Map;

import cn.landis.entity.BookEntity;


public interface BookDao {
	// 新增图书
	public abstract boolean add(BookEntity book);
	// 列出所有图书
	public abstract List<BookEntity> listAll();
	// 删除图书
	public abstract boolean delete(Integer id);
	// 读取一本图书
	public abstract BookEntity getOne(Integer id);
	// 修改图书
	public abstract boolean update(BookEntity book);
	// 按页查询
	public abstract List<BookEntity> findByPage(Integer pageNo,Integer pageSize);
	// 获取总条数
	public abstract long getTotalCountByCondition(Map<String, String> condition);
	// 按条件查询
	public abstract List<BookEntity> findByCondition(Map<String, String> condition,Integer pageNo,Integer pageSize);
	// 查询所有的名字
	public abstract boolean checkName(String name);
	// 获取单个名字
	public abstract boolean getName(Integer id,String name);
	// 获取所有类别
	public abstract List<String> getCategoryList();
	// 因为借书更新
	public abstract boolean updateByBorrow(int bookId,int count);
	// 获取图书被借阅次数
	public abstract long getBorrowCount(Integer bookId);
}
