package com.netease.course.dto;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class Product implements Serializable {
	
	private static final long serialVersionUID = 6096792047370420675L;

	private Integer id;
	
	@Pattern(regexp="^[^><&#]{2,80}$",message="{pattern}")
    @NotNull(message="{notNull}")
	private String title;
	
	private String image;
	
	@Pattern(regexp="^[^><&#]{2,140}$",message="{pattern}")
    @NotNull(message="{notNull}")
	private String summary;
	
	@Pattern(regexp="^[^><&#]{2,1000}$",message="{pattern}")
    @NotNull(message="{notNull}")
	private String detail;
	
	@Min(value=1,message="必须大于或等于1")
	private Integer price;
	
	private Integer buyPrice;
	
	private Boolean isBuy;
	
	private Boolean isSell;
	
	private Integer buyNum;
	
	private Integer saleNum;
	
	private Integer times;
	
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

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(Integer integer) {
		this.buyPrice = integer;
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

	public Integer getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(Integer buyNum) {
		this.buyNum = buyNum;
	}

	public Integer getSaleNum() {
		return saleNum;
	}

	public void setSaleNum(Integer saleNum) {
		this.saleNum = saleNum;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", title=" + title + ", image=" + image + ", summary=" + summary + ", detail="
				+ detail + ", price=" + price + ", buyPrice=" + buyPrice + ", isBuy=" + isBuy + ", isSell=" + isSell
				+ ", buyNum=" + buyNum + ", saleNum=" + saleNum + ", times=" + times + "]";
	}

}
