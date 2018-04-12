package cn.landis.entity;

import java.io.Serializable;

public class CategoryEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String img;
	private String name;
	private String description;

	public CategoryEntity() {
		super();
	}

	public CategoryEntity(int id, String img, String name, String description) {
		super();
		this.id = id;
		this.img = img;
		this.name = name;
		this.description = description;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}