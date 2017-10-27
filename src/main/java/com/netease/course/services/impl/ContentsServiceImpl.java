package com.netease.course.services.impl;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netease.course.dao.ContentsDao;
import com.netease.course.dao.TrxDao;
import com.netease.course.domain.Contents;
import com.netease.course.domain.Trx;
import com.netease.course.dto.Product;
import com.netease.course.dto.ProductList;
import com.netease.course.services.ContentsService;

@Service
public class ContentsServiceImpl implements ContentsService {

	@Autowired
	ContentsDao contentsDao;

	@Autowired
	TrxDao trxDao;

	/**
	 * 商品分页并组装页面展示数据
	 * 
	 * @param pageNO
	 *            页码
	 * @param size
	 *            每页展示条数
	 * @return
	 */
	@Override
	public List<ProductList> getContentsPager(int pageNO, int size, Integer type) {
		int skip = (pageNO - 1) * size;
		List<ProductList> productList = new LinkedList<ProductList>();
		List<Contents> contentList = new LinkedList<Contents>(); 
		if (type != null && type == 1) {
			contentList = contentsDao.getContentsList();
		} else {
			contentList = contentsDao.getContentsPager(skip, size);
		}
		for (Contents content : contentList) {
			ProductList proList = new ProductList();
			proList.setId(content.getId());
			try {
				proList.setImage(new String(content.getIcon(), "utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			proList.setPrice(content.getPrice());
			proList.setTitle(content.getTitle());
			proList.setIsBuy(false);
			proList.setIsSell(false);
			productList.add(proList);
		}
		return productList;
	}

	/**
	 * 获取人购买的内容
	 * @author want
	 * @param Long personId 用户id
	 * @return List
	 */
	public List<Trx> getContentsByPersonId(Long personId) {
		return trxDao.getTrxListByPersonId(personId);
	}
	
	/**
	 * 获取商品列表
	 * @author want
	 * @param personId 用户id
	 * @param pageNO
	 *            页码
	 * @param size
	 *            每页展示条数
	 * @return List
	 */
	@Override
	public List<ProductList> getProductList(Long personId, int pageNO, int size, Integer type) {
		List<ProductList> productList = new LinkedList<ProductList>();
		List<Trx> trxList = trxDao.getTrxListByPersonId(personId);
		List<ProductList> contentsPager = getContentsPager(pageNO, size, type);
		// 拼装返回数据
		if (trxList.isEmpty() || trxList == null) {
			// 处理无购买记录的情况
			for (ProductList proList : contentsPager) {
				List<Trx> list = trxDao.getTrxListByContentId(proList.getId());
				for (Trx trx : list) {
					if (proList.getId() == trx.getContentId()) {
						proList.setIsBuy(true);
						proList.setIsSell(true);
					}
				}
				productList.add(proList);
			}
		} else {
			// 处理有购买记录的情况
			for (ProductList proList : contentsPager) {
				for (Trx trx : trxList) {
					if (proList.getId() == trx.getContentId()) {
						proList.setIsBuy(true);
						proList.setIsSell(true);
					}
				}
				productList.add(proList);
			}
		}
		System.out.println(productList.toString());
		return productList;
	}

	/**
	 * 获得单个产品对象
	 * @author want
	 * @param int id 商品id
	 * @return Contents
	 */
	@Override
	public Contents getContentsById(int id) {
		return contentsDao.getContentsById(id);
	}

	/**
	 * 获得单个产品对象详情
	 * @author want
	 * @param personId 用户id
	 * @param id 商品id
	 * @return Product
	 */
	@Override
	public Product getProductById(int id, Long personId,int userType) {
		int number=0,price = 0,times = 0;
		Product product = new Product();
		Contents content = contentsDao.getContentsById(id);
		//这里只处理了最近一次的购物记录价格&全部个数统计，未做每次的购物金额。
		List<Product> trxProduct = trxDao.getProduct(id, personId);
		product.setIsBuy(false);
		product.setIsSell(false);
		if (trxProduct.size() != 0) {
			if (personId != null) {
				product.setIsBuy(true);
				product.setIsSell(true);
			}
			for (Product trxPro:trxProduct) {
				number += trxPro.getBuyNum();
				price = trxPro.getPrice();
				times++;
			}
		}
		try {
			
			product.setId(content.getId());
			product.setImage(new String(content.getIcon(), "utf-8"));
			product.setPrice(content.getPrice());
			product.setTitle(content.getTitle());
			product.setSummary(content.getAbstract_());
			product.setDetail(new String(content.getText(), "utf-8"));
			product.setBuyNum(number);
			product.setBuyPrice(price);
			product.setSaleNum(number);
			product.setTimes(times);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return product;
	}

	/**
	 * 获得商品总数
	 * @author want
	 * @return Integer 商品总数
	 */
	@Override
	public Integer getContentsCount() {
		return contentsDao.getContentsCount();
	}

	/**
	 * 单个删除
	 * @author want
	 * @param id 商品id
	 * @return Integer
	 */
	@Override
	public Integer delete(int id) {
		return contentsDao.delete(id);
	}
	
	/**
	 * 更新产品内容
	 * @author want
	 * @param Product product
	 * @return Integer
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer updateProduct(Product product) {
		int flag = 0;
		Contents content = contentsDao.getContentsById(product.getId());
		try {
			content.setId(product.getId());
			content.setPrice(product.getPrice());
			content.setTitle(product.getTitle());
			content.setAbstract_(product.getSummary());
			content.setIcon(product.getImage().getBytes("utf-8"));
			content.setText(product.getDetail().getBytes("utf-8"));
			flag = contentsDao.update(content);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return flag;
	}
	
	/**
	 * 新增商品
	 * @author want
	 * @param Product product
	 * @return Integer
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer insertProduct(Product product) {
		int flag = 0;
		Contents content = new Contents();
		try {
			content.setPrice(product.getPrice());
			content.setTitle(product.getTitle());
			content.setAbstract_(product.getSummary());
			content.setIcon(product.getImage().getBytes("utf-8"));
			content.setText(product.getDetail().getBytes("utf-8"));
			contentsDao.insert(content);
			flag = content.getId();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return flag;
	}
	
	/**
	 * 获取多个id的数据
	 * @author want
	 * @param List idsList
	 * @return List
	 */
	@Override
	public List<Contents> getIdsList(List<Integer> idsList) {
		return contentsDao.getIdsList(idsList);
	}

}
