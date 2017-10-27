package com.netease.course.dto;

public class SettleAccount {
	private Integer id;
	private Integer number;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	@Override
	public String toString() {
		return "SettleAccount [id=" + id + ", number=" + number + "]";
	}
	
}
