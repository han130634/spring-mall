package com.netease.course.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netease.course.dao.PersonsDao;
import com.netease.course.domain.Person;
import com.netease.course.services.PersonService;
//自动添加到Spring容器中
@Service
public class PersonServiceImpl implements PersonService{
	
	//自动装配
	@Autowired
    PersonsDao persondao;
    
	@Override
	public Person getUser(Person person) {
		return persondao.getUser(person);
	}

}
