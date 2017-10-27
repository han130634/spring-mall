package com.netease.course.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.netease.course.domain.Person;
/*
 * 登录状态统一校验
 */
public class ApiInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		StringBuffer url = request.getRequestURL();
		String tempContextUrl = url
				.delete(url.length() - request.getRequestURI().length(), url.length())
				.append(request.getServletContext().getContextPath()).toString();
		request.setCharacterEncoding("UTF-8");
        System.out.println("Api interceptor");  
    	Person user = (Person) request.getSession().getAttribute("user");
		if (user == null) {
//			response.setHeader("Access-Control-Allow-Origin", "*");
//	        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//	        response.setHeader("Access-Control-Max-Age", "3600");
//	        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
//			response.setStatus(400);
			//一系列处理后发现session已经失效
//			Enumeration names = request.getHeaderNames();
//	        System.out.println("===================================================================");
//	        while(names.hasMoreElements()){
//	    	  String name = (String) names.nextElement();
//	    	  System.out.println(name + ":" + request.getHeader(name));
//	        }
	        System.out.println("===================================================================");
	        if (request.getHeader("x-requested-with") != null 
	        		&& request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
	        	 /* 设置格式为text/json    */
	            response.setContentType("text/json"); 
	            /*设置字符集为'UTF-8'*/
	        	JSONObject jsonObject = new JSONObject();  
	            jsonObject.put("code", "400");  
	            jsonObject.put("message", "登录状态失效"); 
	            String json = jsonObject.toJSONString();
	        	//如果是ajax请求响应头会有x-requested-with
	            PrintWriter out = response.getWriter();  
	            out.print(json);//session失效
	            out.flush();
	            System.out.println("loseSession");
	            return false;
	        } else {
	            //非ajax请求时，session失效的处理
	        	response.sendRedirect(tempContextUrl + "/login");
				return false;
	        }
		}
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
