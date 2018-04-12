package cn.landis.entity;

import java.io.Serializable;

public class StudentEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String img;
	private String username;
	private String password;

	public StudentEntity() {
		super();
	}

	public StudentEntity(int id, String img, String username, String password) {
		super();
		this.id = id;
		this.img = img;
		this.username = username;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
