package com.netease.course.services;
import java.util.List;

import com.netease.course.dto.BuyList;
import com.netease.course.dto.SettleAccount;
public interface TrxService {
	List<BuyList> getBuyList(Long personId);
	Boolean buy(List<SettleAccount> settleList, Long personId);
}
