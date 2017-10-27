/**
 * 
 */
package com.netease.course.domain;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author want
 *
 */
public class Contents implements Serializable {

	private static final long serialVersionUID = 1490674636594569506L;
	
    private Integer id;

    private Integer price;

    private String title;

    private byte[] icon;

    private String abstract_;

    private byte[] text;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public byte[] getIcon() {
		return icon;
	}

	public void setIcon(byte[] icon) {
		this.icon = icon;
	}

	public String getAbstract_() {
		return abstract_;
	}

	public void setAbstract_(String abstract_) {
		this.abstract_ = abstract_;
	}

	public byte[] getText() {
		return text;
	}

	public void setText(byte[] text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "Content [id=" + id + ", price=" + price + ", title=" + title + ", icon=" + Arrays.toString(icon)
				+ ", abstract_=" + abstract_ + ", text=" + Arrays.toString(text) + "]";
	}
    
	
}
