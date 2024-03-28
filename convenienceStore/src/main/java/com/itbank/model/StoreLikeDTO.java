package com.itbank.model;

public class StoreLikeDTO {

//	IDX       NOT NULL NUMBER        
//	USERID    NOT NULL VARCHAR2(500) 
//	STORE_IDX NOT NULL NUMBER        

	private int idx;
	private String userid;
	private int store_idx;
	
	
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
	public int getStore_idx() {
		return store_idx;
	}
	public void setStore_idx(int store_idx) {
		this.store_idx = store_idx;
	}
	
	
	
}
