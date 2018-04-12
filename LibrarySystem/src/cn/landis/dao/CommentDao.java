package cn.landis.dao;

import java.util.List;

import cn.landis.entity.CommentEntity;

public interface CommentDao {
	// 获取全部评论
	public abstract List<CommentEntity> getAllComment();
	// 新增评论
	public abstract boolean addComment(CommentEntity comment);
}