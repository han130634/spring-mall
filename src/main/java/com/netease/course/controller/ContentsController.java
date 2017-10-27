package com.netease.course.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.netease.course.domain.Person;
import com.netease.course.dto.Product;
import com.netease.course.services.ContentsService;
import com.netease.course.services.TrxService;

@Controller
public class ContentsController {
	@Autowired
	ContentsService contentsService;

	@Autowired
	TrxService trxService;

	Person user;
	
	/**
	 * 商品展示页面
	 * @param session
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show(HttpSession session, Model model, @RequestParam(required = true) Integer id) {
		System.out.println("show");
		Long personId = null;
		int userType = 0;
		user = (Person) session.getAttribute("user");
		if (user != null) {
			personId = user.getId();
			userType = user.getUsertype();
		}
		if (id == null || Integer.parseInt(String.valueOf(id)) <1) {
			return "redirect:/";
		}
		Product product = contentsService.getProductById(id, personId,userType);
		if (product == null) {
			return "redirect:/";
		}
		model.addAttribute("product", product);
		
		return "show";
	}
	
	/**
	 * 商品发布
	 * @author want
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/public", method = RequestMethod.GET)
	public String publish(HttpSession session,Model model) {
		Long personId = null;
		int userType = 0;
		user = (Person) session.getAttribute("user");
		if (user != null) {
			personId = user.getId();
			userType = user.getUsertype();
		}
		if (personId == null || Integer.parseInt(String.valueOf(userType)) != 1) {
			return "redirect:/";
		}
		model.addAttribute("product", new Product());
		return "public";
	}

	/**
	 * 商品新增保存
	 * @author want
	 * @param model
	 * @param product
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("/publicSubmit")
	public String addSave(HttpSession session,Model model, @ModelAttribute("product") @Valid Product product, BindingResult bindingResult) {
		Long personId = null;
		int userType = 0;
		user = (Person) session.getAttribute("user");
		if (user != null) {
			personId = user.getId();
			userType = user.getUsertype();
		}
		if (personId == null || Integer.parseInt(String.valueOf(userType)) != 1) {
			return "redirect:/";
		}
		// 如果模型中存在错误
		if (!bindingResult.hasErrors()) {
			int flag = contentsService.insertProduct(product);
			if (flag > 0) {
				System.out.println(flag);
				return "redirect:/";
			}
		}
		model.addAttribute("product", product);
		return "public";
	}
	
	/**
     * 编辑商品
     * @author want
     * @param model
     * @param id
     * @return
     */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(HttpSession session, Model model, @RequestParam(required = true) int id) {
		Long personId = null;
		int userType = 0;
		user = (Person) session.getAttribute("user");
		if (user != null) {
			personId = user.getId();
			userType = user.getUsertype();
		}
		if (personId == null || Integer.parseInt(String.valueOf(userType)) != 1) {
			return "redirect:/";
		}
		model.addAttribute("product", contentsService.getProductById(id, null,userType));
		return "edit";
	}
	
	/**
	 * 商品修改提交
	 * @author want
	 * @param product
	 * @param bindingResult
	 * @param map
	 * @param session
	 * @return
	 */
	@RequestMapping("/editSubmit")
	public String editSubmit(Model model, HttpSession session, @ModelAttribute("product") @Valid Product product, BindingResult bindingResult ) {
		Long personId = null;
		int userType = 0;
		user = (Person) session.getAttribute("user");
		if (user != null) {
			personId = user.getId();
			userType = user.getUsertype();
		}
		if (personId == null || Integer.parseInt(String.valueOf(userType)) != 1) {
			return "redirect:/";
		}
		// 如果模型中存在错误
		if (!bindingResult.hasErrors()) {
			if (contentsService.updateProduct(product) > 0) {
				System.out.println("suc");
				return "redirect:/";
			}
		}
		model.addAttribute("product", product);
		return "edit";
	}


}
