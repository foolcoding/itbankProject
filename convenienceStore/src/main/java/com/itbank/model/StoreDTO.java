package com.itbank.model;

//		이름      널?       유형             
//		------- -------- -------------- 
//		IDX     NOT NULL NUMBER         
//		NAME    NOT NULL VARCHAR2(500)  
//		ADDRESS NOT NULL VARCHAR2(2000) 
//		PNUM             VARCHAR2(100)  
//		LAT     NOT NULL NUMBER         
//		LNG     NOT NULL NUMBER   


public class StoreDTO {

	
	private int idx;
	private String name;
	private String address;
	private String pnum;
	private String lat;
	private String lng;
	
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPnum() {
		return pnum;
	}
	public void setPnum(String pnum) {
		this.pnum = pnum;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	
	
	
}
