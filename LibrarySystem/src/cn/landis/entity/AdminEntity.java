package cn.landis.entity;

import java.io.Serializable;

public class AdminEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	private String img;
	private String username;
	private String password;

	public AdminEntity() {
		super();
	}

	public AdminEntity(String img, String username, String password) {
		super();
		this.img = img;
		this.username = username;
		this.password = password;
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