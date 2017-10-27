package com.netease.course.services.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netease.course.dao.ContentsDao;
import com.netease.course.dao.TrxDao;
import com.netease.course.domain.Contents;
import com.netease.course.domain.Trx;
import com.netease.course.dto.BuyList;
import com.netease.course.dto.SettleAccount;
import com.netease.course.services.TrxService;
@Service
public class TrxServiceImpl implements TrxService {

	@Autowired
	ContentsDao contentsDao;
	@Autowired
	TrxDao trxDao;
	
	/**
	 * 获取购物记录
	 */
	@Override
	public List<BuyList> getBuyList(Long personId) {
		List<BuyList> resultList = new ArrayList<BuyList>();
		//通过用户id获取购买记录
		List<Trx> trxList = trxDao.getTrxListByPersonId(personId);
		for (Trx trx : trxList) {
			//通过内容id获取内容信息
			Contents content = contentsDao.getContentsById(trx.getContentId());
			if (content == null) {
				continue;
			}
			//构建购买记录要求数据List
			BuyList buyList = new BuyList();
			buyList.setId(trx.getContentId());
			buyList.setTitle(content.getTitle());
			buyList.setBuyPrice(trx.getPrice());
			buyList.setBuyTime(trx.getTime().longValue());
			buyList.setBuyNumber(trx.getNumber());
			buyList.setTotal(trx.getNumber()*trx.getPrice());
			try {
				buyList.setImage(new String(content.getIcon(), "utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			resultList.add(buyList);
		}
		return resultList;
	}
	/**
	 * 购物车
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Boolean buy(List<SettleAccount> settleList, Long personId) {
		for (SettleAccount settle : settleList) {
			if (settle.getNumber()<1) {
				continue;
			}
			Contents content = contentsDao.getContentsById(settle.getId());
			if (content == null) {
				continue;
			}
			Long time = new Date().getTime();
			Trx trx = new Trx();
			trx.setContentId(content.getId());
			trx.setPersonId(personId);
			trx.setPrice(content.getPrice());
			trx.setTime(time);
			trx.setNumber(settle.getNumber());
			if(trxDao.buy(trx)<1){
				return false;
			}
		}
		return true;
	}

}
