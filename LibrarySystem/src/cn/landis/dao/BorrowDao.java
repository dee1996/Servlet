package cn.landis.dao;

import java.util.List;
import java.util.Map;

import cn.landis.entity.BookRank;
import cn.landis.entity.BorrowEntity;

public interface BorrowDao {
	// 获取总条数
	public abstract long getTotalCountByCondition(Map<String, String> condition);
	// 借书
	public abstract boolean addBorrow(BorrowEntity borrowEntity);
	// 检查是否已借过
	public abstract boolean checkBorrow(Integer studentId,Integer bookId);
	// 获取学生全部借的书
	public abstract List<BorrowEntity> getStudentBorrow(Map<String, String> condition,
			Integer pageNo, Integer pageSize,Integer studentId);
	// 获取未归还的书
	public abstract List<BorrowEntity> getNowBorrow(Integer studentId);
	// 获取全部已借的书
	public abstract List<BorrowEntity> getAllBorrow(Map<String, String> condition,Integer pageNo,Integer pageSize);
	// 因为归还更新
	public abstract boolean updateByReturn(BorrowEntity borrow);
	// 获取开始时间
	public abstract String getStartDate(Integer bookId,Integer studentId);
	// 获取图书热度排行榜
	public abstract List<BookRank> getBookRanks();
}