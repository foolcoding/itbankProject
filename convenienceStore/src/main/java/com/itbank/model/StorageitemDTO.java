package com.itbank.model;

public class StorageitemDTO {

	
//	IDX             NOT NULL NUMBER        
//	ORDERDETAIL_IDX NOT NULL VARCHAR2(100) 
//	USERID          NOT NULL VARCHAR2(500) 
//	TOTALCOUNT      NOT NULL NUMBER        
//	RESCOUNT        NOT NULL NUMBER        
//	STATUS                   NUMBER        
	
	private int idx;
	private String orderDetail_idx;
	private String userid;
	private int totalCount;
	private int rescount;
	private int status;
	
	private String name;
	

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getOrderDetail_idx() {
		return orderDetail_idx;
	}

	public void setOrderDetail_idx(String orderDetail_idx) {
		this.orderDetail_idx = orderDetail_idx;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getRescount() {
		return rescount;
	}

	public void setRescount(int rescount) {
		this.rescount = rescount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

}
