package com.netease.course.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.netease.course.domain.Trx;
import com.netease.course.dto.Product;

public interface TrxDao {
	@Select({"<script>",
	    "SELECT * FROM trx",
	    "WHERE personId=#{personId}",
//	    "<when contentId='contentId!=null'>",
//	    "AND contentId = #{contentId}",
//	    "</when>",
	    "</script>"})
	//@Select("SELECT * FROM trx WHERE personId = #{personId} ;")
	public List<Trx> getTrxListByPersonId(@Param(value = "personId") Long personId);
	
	@Select("SELECT * FROM trx WHERE contentId = #{contentId} ;")
	public List<Trx> getTrxListByContentId(@Param(value = "contentId") Integer contentId);
	
	@Select({"<script>",
	    "SELECT con.id,con.title,con.icon AS image,con.abstract AS summary,con.text AS detail,con.price,trx.price AS buyPrice,trx.number AS buyNum,trx.time AS buyTime FROM trx LEFT JOIN Content AS con ON trx.contentId =con.id ",
	    "WHERE 1=1 ",
	    "<when test='personId!=null'>",
	    "AND trx.personId =#{personId} ",
	    "</when>",
	    "<when test='contentId!=null'>",
	    "AND trx.contentId =#{contentId} ",
	    "</when>",
	    "ORDER BY time DESC;",
	    "</script>"})
	public List<Product> getProduct(@Param(value = "contentId") Integer contentId, @Param(value = "personId") Long personId);

	@Insert("INSERT trx(contentId, personId, price,time,number) VALUES(#{contentId},#{personId},#{price},#{time},#{number}) ;")
	public int buy(Trx trx);
	
}