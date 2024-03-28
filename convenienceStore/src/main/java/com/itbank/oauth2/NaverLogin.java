package com.itbank.oauth2;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.itbank.model.OauthUserDTO;

@Component
public class NaverLogin implements OauthLogin {

	private static final String CLIENT_ID = "xKPOaZQDGVp6QBc7XqVT";
	private static final String CLIENT_SECRET = "MB6CDOfQn0";
	private static final String REDIRECT_URL = "http://localhost:8080/convenienceStore/oauth2_naver";
	private static final String SESSION_STATE = "naver_oauth_state";
	private static final String PROFILE_API_URL = "https://openapi.naver.com/v1/nid/me";
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	// 네아로 인증 URL 생성
	@Override
	public String getAuthorizationUrl(HttpSession session) {
		String state = UUID.randomUUID().toString().replace("-", "");
		session.setAttribute(SESSION_STATE, state);
		OAuth20Service oauthService = new ServiceBuilder(CLIENT_ID)
											.apiSecret(CLIENT_SECRET)
											.callback(REDIRECT_URL)
											.debug()
											.build(NaverLoginAPI.getInstance());
		return oauthService.getAuthorizationUrl(state);
	}
	
	// 네아로 콜백 및 액세스 토큰 획득
	@Override
	public String getAccessToken(HttpSession session, String code, String state) throws IOException, InterruptedException, ExecutionException {
		String sessionState = (String) session.getAttribute(SESSION_STATE);
		if(StringUtils.pathEquals(sessionState, state)) {
			OAuth20Service oauthService = new ServiceBuilder(CLIENT_ID)
												.apiSecret(CLIENT_SECRET)
												.callback(REDIRECT_URL)
												.debug()
												.build(NaverLoginAPI.getInstance());
			
			OAuth2AccessToken accessToken = oauthService.getAccessToken(code);
			return accessToken.getAccessToken();
		}
		return null;
	}
	
	@Override
	public String getUserProfile(String oauthToken) throws InterruptedException, ExecutionException, IOException {
		OAuth20Service oauthService = new ServiceBuilder(CLIENT_ID)
											.apiSecret(CLIENT_SECRET)
											.callback(REDIRECT_URL)
											.debug()
											.build(NaverLoginAPI.getInstance());
		
		OAuthRequest request = new OAuthRequest(Verb.GET, PROFILE_API_URL);
		request.addQuerystringParameter("CLIENT_ID", CLIENT_ID);
		request.addQuerystringParameter("CLIENT_SECRET", CLIENT_SECRET);
		request.addQuerystringParameter("ACCESS_TOKEN", oauthToken);
		request.addQuerystringParameter("GRANT_TYPE", "GET");
		request.addQuerystringParameter("SERVICE_PROVIDER", "NAVER");
		
		oauthService.signRequest(oauthToken, request);
		
		Response response = oauthService.execute(request);
		
		return response.getBody();
	}

	
	//	getOauthUser : 네이버에서 받은 json 객체 중에서 내가 원하는 값만 저장
	@Override
	public OauthUserDTO getOauthUser(String apiResult) {
		OauthUserDTO dto = null;
		try {
			JsonNode node = objectMapper.readTree(apiResult);
			System.out.println(node);
			JsonNode response = node.findValue("response");
			System.out.println(response);
//			dto = objectMapper.treeToValue(response, OauthUserDTO.class);
			dto = new OauthUserDTO();
			dto.setId(response.get("id").asText());
			dto.setNickname(response.get("nickname").asText());
			dto.setEmail(response.get("email").asText());
			dto.setName(response.get("name").asText());
			return dto;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return dto;
	}
}
