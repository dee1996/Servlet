package cn.dee.dao;

import cn.dee.action.BookAction;

public interface BookDao {
	public BookAction getBook(String name);
}