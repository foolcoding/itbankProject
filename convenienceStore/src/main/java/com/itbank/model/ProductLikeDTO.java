package com.itbank.model;

public class ProductLikeDTO {

	
//	이름          널?       유형            
//
//	IDX         NOT NULL NUMBER        
//	USERID      NOT NULL VARCHAR2(500) 
//	PRODUCT_IDX NOT NULL NUMBER       
	
	private int idx;
	private String userid;
	private int product_idx;
	
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public int getProduct_idx() {
		return product_idx;
	}
	public void setProduct_idx(int product_idx) {
		this.product_idx = product_idx;
	}
	
	

}
