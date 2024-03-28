package com.itbank.model;

import java.sql.Date;

public class ChatDTO {

//	IDX      NOT NULL NUMBER        
//	USERID1  NOT NULL VARCHAR2(500) 
//	USERID2           VARCHAR2(500) 
//	CHATDATE          DATE          
//	STATUS            NUMBER     

	
	private	int idx;
	private String userid1;
	private String userid2;
	private Date chatDate;
	private int status;
	
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getUserid1() {
		return userid1;
	}
	public void setUserid1(String userid1) {
		this.userid1 = userid1;
	}
	public String getUserid2() {
		return userid2;
	}
	public void setUserid2(String userid2) {
		this.userid2 = userid2;
	}
	public Date getChatDate() {
		return chatDate;
	}
	public void setChatDate(Date chatDate) {
		this.chatDate = chatDate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
}
