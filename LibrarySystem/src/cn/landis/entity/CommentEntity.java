package cn.landis.entity;

import java.io.Serializable;

/**
 * the comment entity
 * @author Landis
 *
 */
public class CommentEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private int studentId;
	private String studentComment;
	private String commentDate;

	public CommentEntity() {
		super();
	}

	public CommentEntity(int id, int studentId, String studentComment,
			String commentDate) {
		super();
		this.id = id;
		this.studentId = studentId;
		this.studentComment = studentComment;
		this.commentDate = commentDate;
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

	public String getStudentComment() {
		return studentComment;
	}

	public void setStudentComment(String studentComment) {
		this.studentComment = studentComment;
	}

	public String getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(String commentDate) {
		this.commentDate = commentDate;
	}

}