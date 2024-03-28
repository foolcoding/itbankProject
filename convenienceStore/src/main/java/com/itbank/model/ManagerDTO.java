package com.itbank.model;

//		이름        널?       유형            
//		--------- -------- ------------- 
//		IDX       NOT NULL NUMBER        
//		USERID    NOT NULL VARCHAR2(500) 
//		NAME      NOT NULL VARCHAR2(500) 
//		STORE_IDX NOT NULL NUMBER        
//		STATUS             NUMBER  

public class ManagerDTO {

	private int idx;
	private String userid;
	private String name;
	private int store_idx;
	private int status;
	
	
	private String store_name;
	
	
	
	public String getStore_name() {
		return store_name;
	}
	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStore_idx() {
		return store_idx;
	}
	public void setStore_idx(int store_idx) {
		this.store_idx = store_idx;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
