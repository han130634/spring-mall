package com.netease.course.controller;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.netease.course.domain.Person;
import com.netease.course.dto.SettleAccount;
import com.netease.course.services.ContentsService;
import com.netease.course.services.TrxService;

@RestController
public class ApiController {
	
	@Autowired
	ContentsService contentsService;
	
	@Autowired
	TrxService trxService;

	/*
	 * 上传图片保存
	 */
	@RequestMapping("/api/upload")
	public Object upPictureSave(ModelMap map, MultipartFile file, HttpServletRequest request) {
		// 允许上传的文件最大大小(100M,单位为byte)
		int maxSize = 1024 * 1024 * 1;
		// 文件扩展名称
		String extName, suffixName = "";
		// 允许上传的文件类型
		String fileType = "gif,png,bmp,jpeg,jpg";
		map.addAttribute("code", 400);
		map.addAttribute("message", "异常");
		map.addAttribute("result", false);
		// 如果选择了文件
		if (file != null) {
			// 如果文件大小不为0
			if (file.getSize() > 0 && file.getSize() < maxSize) {
				// 获得上传位置
				String path = request.getServletContext().getRealPath("/images");
				// 获取文件后缀名 例如:.jpg
				suffixName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."))
						.toLowerCase().trim();
				// 获取文件后缀类型
				extName = suffixName.substring(suffixName.indexOf(".") + 1);
				// 判断是否为允许上传的文件类型
				if (Arrays.<String>asList(fileType.split(",")).contains(extName)) {
					// 生成文件名
					String filename = UUID.randomUUID().toString() + suffixName;
					File tempFile = new File(path, filename);
					try {
						// 保存文件
						file.transferTo(tempFile);
						StringBuffer url = request.getRequestURL();
						// http://localhost:8080/
						// String tempContextUrl = url.delete(url.length() -
						// request.getRequestURI().length(),url.length()).append("/").toString();
						// http://localhost:8080/spring-mall/
						String tempContextUrl = url
								.delete(url.length() - request.getRequestURI().length(), url.length())
								.append(request.getServletContext().getContextPath()).toString();
						// 更新数据
						map.addAttribute("code", 200);
						map.addAttribute("message", "成功");
						map.addAttribute("result", (tempContextUrl + "/images/" + filename).toString());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return map;
	}

	/**
	 * 删除单个产品对象Action
	 * @param map
	 * @param id 商品id
	 * @return Object
	 */
	@RequestMapping(value = "/api/delete", method = RequestMethod.POST)
	public Object delete(ModelMap map, @RequestParam int id) {
		map.addAttribute("code", 400);
		map.addAttribute("message", "异常");
		map.addAttribute("result", false);
		if (contentsService.getContentsById(id) != null && contentsService.delete(id) > 0) {
			map.addAttribute("code", 200);
			map.addAttribute("message", "成功");
			map.addAttribute("result", true);
		}

		return map;
	}
	/**
	 * 商品购买接口
	 * @author want
	 * @param session
	 * @param map
	 * @param settleList 购买商品信息list
	 * @return Object
	 */
	@RequestMapping(value = "/api/buy", method = RequestMethod.POST)
	public Object buy(HttpSession session, ModelMap map, @RequestBody List<SettleAccount> settleList) {
		Long personId = ((Person) session.getAttribute("user")).getId();
		if (personId!=null && trxService.buy(settleList, personId)) {
			map.put("code", 200);
			map.put("message", "购买成功");
			map.put("result", true);
		} else {
			map.put("code", 403);
			map.put("message", "购买失败");
			map.put("result", false);
		}
		return map;
	}
}
