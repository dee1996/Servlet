package cn.landis.entity;

import java.io.Serializable;

public class BorrowEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private int studentId;
	private String studentName;
	private int bookId;
	private String bookName;
	private String startDate;
	private String endDate;
	// 如果为1则是已归还，为0则是未归还
	private int studentStatus;
	private String hasReturn;
	private int money;

	public BorrowEntity() {
		super();
	}

	public BorrowEntity(int id, int studentId, String studentName, int bookId,
			String bookName, String startDate, String endDate,
			int studentStatus, String hasReturn, int money) {
		super();
		this.id = id;
		this.studentId = studentId;
		this.studentName = studentName;
		this.bookId = bookId;
		this.bookName = bookName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.studentStatus = studentStatus;
		this.hasReturn = hasReturn;
		this.money = money;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getStudentStatus() {
		return studentStatus;
	}

	public void setStudentStatus(int studentStatus) {
		this.studentStatus = studentStatus;
	}

	public String getHasReturn() {
		return hasReturn;
	}

	public void setHasReturn(String hasReturn) {
		this.hasReturn = hasReturn;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

}