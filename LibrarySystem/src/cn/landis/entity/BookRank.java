package cn.landis.entity;

public class BookRank {
	private String bookName;
	private int count;

	public BookRank() {
		super();
	}

	public BookRank(String bookName, int count) {
		super();
		this.bookName = bookName;
		this.count = count;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}