package com.itbank.model;

import java.sql.Date;

public class UserCouponDTO {
	
//	이름         널?       유형            
//			---------- -------- ------------- 
//	IDX        NOT NULL NUMBER        
//	COUPON_IDX NOT NULL NUMBER        
//	USERID     NOT NULL VARCHAR2(500) 
//	ISSUEDATE           DATE          
//	ENDDATE    NOT NULL DATE          
//	USEDDATE            DATE    
	
	private int idx;
	private int coupon_idx;
	private String userid;
	private Date issueDate;
	private Date endDate;
	private Date usedDate;
	
	
	private String title;
	private String discValue;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDiscValue() {
		return discValue;
	}
	public void setDiscValue(String discValue) {
		this.discValue = discValue;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public int getCoupon_idx() {
		return coupon_idx;
	}
	public void setCoupon_idx(int coupon_idx) {
		this.coupon_idx = coupon_idx;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public Date getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getUsedDate() {
		return usedDate;
	}
	public void setUsedDate(Date usedDate) {
		this.usedDate = usedDate;
	}
	
	
	
}
