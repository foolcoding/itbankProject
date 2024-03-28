package com.itbank.model;

public class ProductDTO {

	
//	이름           널?       유형             
//
//	IDX          NOT NULL NUMBER         
//	CATEGORY_IDX NOT NULL NUMBER         
//	NAME         NOT NULL VARCHAR2(500)  
//	PRICE        NOT NULL NUMBER         
//	CONTENT               VARCHAR2(4000) 
//	IMAGE        NOT NULL VARCHAR2(1000) 
//	EVENT                 VARCHAR2(100)
	
	private int idx;
	private int category_idx;
	private String name;
	private int price;
	private String content;
	private String image;
	private String event;

	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public int getCategory_idx() {
		return category_idx;
	}
	public void setCategory_idx(int category_idx) {
		this.category_idx = category_idx;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	
	
}
