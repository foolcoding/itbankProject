package com.itbank.model;



//		IDX      NOT NULL NUMBER        
//		USERID   NOT NULL VARCHAR2(500) 
//		USERPW   NOT NULL VARCHAR2(500) 
//		EMAIL    NOT NULL VARCHAR2(500) 
//		NICKNAME NOT NULL VARCHAR2(500) 
//		ROLE     NOT NULL NUMBER        
//		PNUM     NOT NULL VARCHAR2(500) 
//		NAVER_ID          VARCHAR2(500) 

public class MemberDTO {


	private int idx;
	private String userid;
	private String userpw;
	private String email;
	private String nickname;
	private int role;
	private String pnum;
	
	
	private String naver_id;
	
	
	
	public String getNaver_id() {
		return naver_id;
	}
	public void setNaver_id(String naver_id) {
		this.naver_id = naver_id;
	}
	public String getPnum() {
		return pnum;
	}
	public void setPnum(String pnum) {
		this.pnum = pnum;
	}
	private String name;
	private String store_name;
	private int store_idx;
	
	
	
	public int getStore_idx() {
		return store_idx;
	}
	public void setStore_idx(int store_idx) {
		this.store_idx = store_idx;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
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
	public String getUserpw() {
		return userpw;
	}
	public void setUserpw(String userpw) {
		this.userpw = userpw;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	
}
