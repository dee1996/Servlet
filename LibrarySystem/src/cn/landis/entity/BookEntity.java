package cn.landis.entity;

import java.io.Serializable;

public class BookEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String img;
	private String category;
	private String name;
	private String description;
	private int count;

	public BookEntity() {
		super();
	}

	public BookEntity(int id, String img, String category, String name,
			String description, int count) {
		super();
		this.id = id;
		this.img = img;
		this.category = category;
		this.name = name;
		this.description = description;
		this.count = count;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}