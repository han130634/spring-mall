package com.netease.course.dto;

import java.io.Serializable;

public class ProductList implements Serializable{
	
	private static final long serialVersionUID = 162196044330482043L;

	private Integer id;
	
	private String title;
	
	private String image;
	
	private Integer price;
	
	private Boolean isBuy;
	
	private Boolean isSell;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Boolean getIsBuy() {
		return isBuy;
	}

	public void setIsBuy(Boolean isBuy) {
		this.isBuy = isBuy;
	}

	public Boolean getIsSell() {
		return isSell;
	}

	public void setIsSell(Boolean isSell) {
		this.isSell = isSell;
	}

	@Override
	public String toString() {
		return "ProductList [id=" + id + ", title=" + title + ", image=" + image + ", price=" + price + ", isBuy="
				+ isBuy + ", isSell=" + isSell + "]";
	}
	
}
