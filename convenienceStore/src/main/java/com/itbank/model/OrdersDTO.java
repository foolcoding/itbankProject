package com.itbank.model;

import java.sql.Date;

public class OrdersDTO {
	
//	IDX        NOT NULL VARCHAR2(300) 
//	USERID     NOT NULL VARCHAR2(500) 
//	STORE_IDX  NOT NULL NUMBER        
//	PICKUPCODE NOT NULL VARCHAR2(100) 
//	STATUS              NUMBER        
//	ORDERDATE           DATE          
//	PICKUPTIME          DATE          
//	PAYMENTKEY NOT NULL VARCHAR2(300) 
//	AMOUNT     NOT NULL NUMBER 
	
	private int idx;
	private String userid;
	private int store_idx;
	private String pickupCode;
	private int status;
	private Date orderDate;
	private Date pickupTime;
	private String paymentKey;
	private int amount;
	
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
	public String getPickupCode() {
		return pickupCode;
	}
	public void setPickupCode(String pickupCode) {
		this.pickupCode = pickupCode;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public Date getPickupTime() {
		return pickupTime;
	}
	public void setPickupTime(Date pickupTime) {
		this.pickupTime = pickupTime;
	}
	public String getPaymentKey() {
		return paymentKey;
	}
	public void setPaymentKey(String paymentKey) {
		this.paymentKey = paymentKey;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	
	
	
}
