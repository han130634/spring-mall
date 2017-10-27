package com.netease.course.domain;

import java.io.Serializable;

public class Person implements Serializable{
	private static final long serialVersionUID = 5649268942672314217L;
	
	private Long id;
	private String username;
	private String nickName;
	private String password;
	private Integer usertype;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getUsertype() {
		return usertype;
	}

	public void setUsertype(Integer usertype) {
		this.usertype = usertype;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", username=" + username + ", nickName=" + nickName + ", password=" + password
				+ ", usertype=" + usertype + "]";
	}
	
	
}
