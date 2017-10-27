package com.netease.course.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.netease.course.domain.Person;


public class SellerInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		StringBuffer url = request.getRequestURL();
		String tempContextUrl = url
				.delete(url.length() - request.getRequestURI().length(), url.length())
				.append(request.getServletContext().getContextPath()).toString();
		request.setCharacterEncoding("UTF-8");
        System.out.println("seller interceptor in ");
    	Person user = (Person) request.getSession().getAttribute("user");
		if (user == null) {
			//一系列处理后发现session已经失效
	        if (request.getHeader("x-requested-with") != null 
	        		&& request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
	        	//如果是ajax请求响应头会有x-requested-with  
	            PrintWriter out = response.getWriter();  
	            out.print("loseSession");//session失效
	            out.flush();
	            return false;
	        } else {
	            //非ajax请求时，session失效的处理
	        	response.sendRedirect(tempContextUrl + "/login");
				return false;
	        }
			
		} else if (user.getUsertype() != 1) {
			System.out.println("seller interceptor interrupt");
			response.sendRedirect(tempContextUrl + "/");
			return false;
		}
		
		
		System.out.println("seller interceptor out ");
		return true;
	}

}
