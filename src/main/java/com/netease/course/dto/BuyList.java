package com.netease.course.dto;

import java.io.Serializable;

public class BuyList implements Serializable{
	
	private static final long serialVersionUID = -1629778546338477926L;

	private Integer id;
	
	private String title;
	
	private String image;
	
	private Integer buyPrice;
	
	private long buyTime;
	
	private Integer buyNumber;
	
	private Integer total;

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

	public Integer getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(Integer buyPrice) {
		this.buyPrice = buyPrice;
	}

	public long getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(long buyTime) {
		this.buyTime = buyTime;
	}

	
	public Integer getBuyNumber() {
		return buyNumber;
	}

	public void setBuyNumber(Integer buyNumber) {
		this.buyNumber = buyNumber;
	}

	
	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "BuyList [id=" + id + ", title=" + title + ", image=" + image + ", buyPrice=" + buyPrice + ", buyTime="
				+ buyTime + ", buyNumber=" + buyNumber + ", total=" + total + "]";
	}

}
