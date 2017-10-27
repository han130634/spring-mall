package com.netease.course.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
//import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
//import org.apache.ibatis.mapping.StatementType;

import com.netease.course.domain.Contents;

public interface ContentsDao {
	/**
     * 获得商品信息并分页
     */
	@Results({ 
		@Result(property = "id", column = "id"),
		@Result(property = "title", column = "title") }
	)
	@Select("SELECT id,title,price,icon,text,abstract FROM content ")
    public List<Contents> getContentsList();
    
	/**
     * 获得商品信息并分页
     */
	@Results({ 
		@Result(property = "id", column = "id"),
		@Result(property = "title", column = "title") }
	)
	@Select("SELECT id,title,price,icon,text,abstract FROM content LIMIT #{skip},#{size}")
    public List<Contents> getContentsPager(@Param("skip") int skip,@Param("size") int size);
    
    /**
     * 获得单个商品通过编号
     */
    @Results({ 
		@Result(property = "id", column = "id"),
		@Result(property = "title", column = "title") }
	)
	@Select("SELECT id,title,price,icon,text,abstract AS abstract_ FROM content WHERE id=#{id} ;")
    public Contents getContentsById(int id);
    
    /**
     * 获得商品总数
     */
    @Select("SELECT count(*) FROM content")
    public int getContentsCount();
    
    /**
     * 新增加商品
     */
    @Insert("insert into content(price, title, icon, abstract, text) values(#{price},#{title},#{icon},#{abstract_},#{text})")	
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
//    @SelectKey(statement="SELECT LAST_INSERT_ID()",  keyProperty="id", statementType=StatementType.STATEMENT,resultType=String.class, before = false)
    public int insert(Contents content);
    
    /**
     * 删除商品
     */
    @Delete("delete from content where id=#{id}")
    public int delete(int id);
    
    @Select("<script>"
            + "SELECT id,title,price,icon,text,abstract FROM content WHERE id IN "
            + "<foreach item='item' index='index' collection='idsList' open='(' separator=',' close=')'>"
                + "#{item}"
            + "</foreach>"
        + "</script>")
    @Results(value = { @Result(column = "id", property = "id") })
    public List<Contents> getIdsList(@Param("idsList") List<Integer> idsList);
    
    /**
     * 修改商品
     */
	@Update("update content set price=#{price},title=#{title},icon=#{icon},abstract=#{abstract_},text=#{text} where id = #{id}")
	public int update(Contents content);
	
}
