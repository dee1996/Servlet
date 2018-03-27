package cn.landis.entity;

import java.io.Serializable;
/**
 * the reply entity
 * @author Landis
 *
 */

public class CommentDetailEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private int studentId;
	private int commentId;
	private String comment;
	private String commentDate;
	private int status;

	public CommentDetailEntity() {
		super();
	}

	public CommentDetailEntity(int id, int studentId, int commentId, String comment,
			String commentDate, int status) {
		super();
		this.id = id;
		this.studentId = studentId;
		this.commentId = commentId;
		this.comment = comment;
		this.commentDate = commentDate;
		this.status = status;
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

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(String commentDate) {
		this.commentDate = commentDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}