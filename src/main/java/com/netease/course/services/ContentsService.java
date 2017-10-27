package com.netease.course.services;

import java.util.List;

import com.netease.course.domain.Contents;
import com.netease.course.domain.Trx;
import com.netease.course.dto.Product;
import com.netease.course.dto.ProductList;


public interface ContentsService {
	//分页
    List<ProductList> getContentsPager( int pageNO, int size, Integer type);
    
    //获取用户商品
    List<Trx> getContentsByPersonId(Long personId);
    
    //分页
    List<ProductList> getProductList(Long personId, int pageNO, int size, Integer type);
    
    //获得单个商品对象
    Contents getContentsById(int id);
    
    //判断用户是否购买商品
    Product getProductById(int id, Long personId, int userType);
    
    //获得商品总数
    Integer getContentsCount();

    //删除单个
    Integer delete(int id);
    
    //根据多个id获取内容
    List<Contents> getIdsList(List<Integer> idsList);
    
    //更新
    Integer updateProduct(Product product);
    //添加
    Integer insertProduct(Product product);

}
