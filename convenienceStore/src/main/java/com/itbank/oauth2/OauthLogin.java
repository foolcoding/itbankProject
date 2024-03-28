package com.itbank.oauth2;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpSession;

import com.itbank.model.OauthUserDTO;

public interface OauthLogin {

	String getAuthorizationUrl(HttpSession session);
	
	String getAccessToken(HttpSession session, String code, String state) throws IOException, InterruptedException, ExecutionException;

	String getUserProfile(String accessToken) throws InterruptedException, ExecutionException, IOException;
	
	OauthUserDTO getOauthUser(String apiResult);
}
