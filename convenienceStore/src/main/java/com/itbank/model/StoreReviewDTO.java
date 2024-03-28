package com.itbank.model;

import java.sql.Date;

public class StoreReviewDTO {

//	IDX        NOT NULL NUMBER         
//	STORE_IDX  NOT NULL NUMBER         
//	USERID     NOT NULL VARCHAR2(500)  
//	SCORE               NUMBER         
//	CONTENT    NOT NULL VARCHAR2(4000) 
//	REVIEWDATE          DATE    
	
	private int idx;
	private int store_idx;
	private String userid;
	private double score;
	private String content;
	private Date reviewDate;
	
	
	private String name;
	
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public int getStore_idx() {
		return store_idx;
	}
	public void setStore_idx(int store_idx) {
		this.store_idx = store_idx;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}

	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getReviewDate() {
		return reviewDate;
	}
	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}
	
	
}
