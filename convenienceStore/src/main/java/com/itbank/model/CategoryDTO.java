package com.itbank.model;

public class CategoryDTO {
	
//	이름           널?       유형            

//	IDX          NOT NULL NUMBER        
//	CATEGORYNAME NOT NULL VARCHAR2(500) 
	
	
	private int idx;
	private String categoryName;
	
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	
}
