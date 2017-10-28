package com.netease.course.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.netease.course.domain.Person;
import com.netease.course.services.ContentsService;

@Controller
@RequestMapping("/")
public class IndexController {
	@Autowired
	ContentsService contentsService;

	/**
	 * 首页，增加所有内容的分页未购买页面，由于需要改动比较大，未做分页处理；
	 * TODO 【js注释了删除跳转代码，由于分页有一个小问题当前就是页面只剩一个再删除，需跳转页码】；
	 * 
	 * @author want
	 * @param map
	 * @param model
	 * @param pageNO
	 *            页码
	 * @return String
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String index(HttpSession session, Model model,
			@RequestParam(required = false, defaultValue = "1") int pageNO,@RequestParam(required = false) Integer type) {
		Person user = (Person) session.getAttribute("user");
		System.out.println("index");
		int size = 10;
		model.addAttribute("size", size);
		model.addAttribute("pageNO", pageNO);
		model.addAttribute("count", contentsService.getContentsCount());
		model.addAttribute("productList", contentsService.getProductList((user != null && user.getId() != null) ? (Long) user.getId() : null, pageNO, size, type));

		return "index";
	}
}
