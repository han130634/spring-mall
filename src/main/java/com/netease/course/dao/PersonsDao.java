package com.netease.course.dao;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.netease.course.domain.Person;

public interface PersonsDao {
	
	@Results({ 
		@Result(property = "id", column = "id"),
		@Result(property = "userName", column = "userName") }
	)
	@Select("Select * from person")
	public List<Person> getPersonsList();
	
	@Results({ 
		@Result(property = "id", column = "id"),
		@Result(property = "username", column = "userName"),
		@Result(property = "nickName", column = "nickName"),
		@Result(property = "usertype", column = "userType")}
	)
	@Select("Select id,userName,nickName,userType from person where userName=#{username} and password=#{password} ")
	public Person getUser(Person person);
	
}
