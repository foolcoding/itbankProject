package com.itbank.oauth2;

import com.github.scribejava.core.builder.api.DefaultApi20;

public class NaverLoginAPI extends DefaultApi20 {
	
	protected NaverLoginAPI() {}
	
	private static NaverLoginAPI instance = new NaverLoginAPI();
	public static NaverLoginAPI getInstance() {
		return instance;
	}

	@Override
	public String getAccessTokenEndpoint() {	// 사용자 접근 권한
		return "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code";
	}

	@Override
	protected String getAuthorizationBaseUrl() {	// 사용자 동의란
		return "https://nid.naver.com/oauth2.0/authorize";
	}

	
}
