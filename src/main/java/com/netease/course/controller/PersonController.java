package com.netease.course.controller;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netease.course.domain.Result;
import com.netease.course.domain.Person;
import com.netease.course.services.PersonService;
import com.netease.course.services.TrxService;

@Controller
public class PersonController {
	
	@Autowired
	PersonService personService;
	
	@Autowired
	TrxService trxService;
	
	/**
	 * 登录页面
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String index() {
		System.out.println("login");
		return "login";
	}
	
	/**
	 * 登录状态判断
	 * @param request
	 * @return
	 */
	private Boolean loginStatus(HttpServletRequest request) {
		//初始化session
		HttpSession session = request.getSession(false);
		//除第一次登录外【sessionPassword存在表明不是第一次登录需要让session失效,后面重新设置密码】
		if (session != null) {
			Person user = (Person) session.getAttribute("user");
			if (user != null ) {
				return true;
			}
			session.invalidate();
		}
		
		return false;
	}
	/**
	 * 登录
	 * @param username
	 * @param password
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/api/login", method = RequestMethod.POST)
	@ResponseBody
	public Object login(@RequestParam("userName") String username, @RequestParam("password") String password,
			ModelMap map, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		System.out.println("login user info : " + username + " :: " + password);
		Result result = new Result();
		
		if ((username.length() < 5 || username.length() > 20) || password.length() != 32) {
			System.out.println("failed");
			result.setCode(400);
			result.setMessage("failed");
			result.setResult(false);
			return result;
		}
		
		System.out.println("in");
		//判断当前用户是否已经登录状态，未登录需要进行登录，否则直接跳转至user页面。
		if ( loginStatus(request) ) {
			System.out.println("logined");
			result.setCode(200);
			result.setMessage("success");
			result.setResult(true);
			return result;
		}
		
		String cookieUser = null;//cookie中存取user的值
		//通过request获取cookie
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				//System.out.println(cookie.getName()+":"+cookie.getValue());
				if (cookie.getName().equals("username")) {
					cookieUser = cookie.getValue();
					break;
				}
			}
		}
		
		//获取用户信息，判断用户是否存在。
		Person person = new Person();
		person.setUsername(username);
		person.setPassword(password);
		Person users = personService.getUser(person);
		
		//是否登录成功，成功设置用户相关session和cookie信息，否则，失败跳转至登录页面或者错误页面。
		if (users != null && users.getId() != null) {
			//设置session登录状态，设置用户名cookie
			System.out.println(users.getId()+"::"+users.getUsername()+"::"+users.getPassword());
			HttpSession session = request.getSession();
			session.setAttribute("user", users);
			//用户输入账号不为空并且不等于cookie中的user的已存在的数据。
			//创建user的cookie并且设置30分钟
			Cookie userCookie = new Cookie("username", users.getUsername());
			userCookie.setMaxAge(30 * 60);
			response.addCookie(userCookie);
			//区别cookieUser有没有值，当cookieUser存在时输出
			if (cookieUser != null && cookieUser != "") {
				System.out.println("current user login : " + users.getUsername());
			}
			
			result.setCode(200);
			result.setMessage("success");
			result.setResult(true);
			return result;
		}
		
		System.out.println("login failed user not exists!");
		result.setCode(400);
		result.setMessage("failed");
		result.setResult(false);
		return result;
		
	}
	/**
	 * 退出
	 * @author want
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//初始化session
		HttpSession session = request.getSession();
		session.invalidate();
		response.sendRedirect(request.getContextPath()+"/login");
	}
	
	/**
	 * 购物记录【后续增加翻页功能】
	 * @author want
	 * @param model
	 * @param pageNO
	 * @return
	 */
	@RequestMapping(value = "/account", method = RequestMethod.GET)
	public String account(Model model,@RequestParam(required=false,defaultValue="1") int pageNO, HttpSession session) {
		Person user = (Person) session.getAttribute("user");
		if (user == null ) {
			return "redirect:/";
		}
		model.addAttribute("buyList",trxService.getBuyList(user.getId()));
		return "account";
	}
	
	/**
	 * 购物车
	 * @author want
	 * @return
	 */
	@RequestMapping(value = "/settleAccount", method = RequestMethod.GET)
	public String settleAccount(HttpServletRequest request) {
		if (!loginStatus(request)) {
			return "redirect:/";
		}
		return "settleAccount";
	}
}
