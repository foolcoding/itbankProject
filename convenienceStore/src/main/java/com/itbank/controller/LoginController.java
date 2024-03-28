package com.itbank.controller;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.itbank.model.MemberDTO;
import com.itbank.model.OauthUserDTO;
import com.itbank.oauth2.NaverLogin;
import com.itbank.oauth2.OauthLogin;
import com.itbank.service.MemberService;

@Controller
public class LoginController {
	
	@Autowired private NaverLogin naverLogin;
	@Autowired private MemberService memberService;


	
	@GetMapping("/oauth2_intergrated")
	public ModelAndView oauth2_intergrated(String code, String state, HttpSession session) throws IOException, InterruptedException, ExecutionException {
	    ModelAndView mav = new ModelAndView("oauth2");
	    
	    OauthLogin oauthLogin = null;
	    String provider = (String) session.getAttribute("provider");
	    
	    switch(provider) {
	        case "NAVER":    oauthLogin = naverLogin;    break;
	    }
	    // 2) 로그인 정보로 액세스 토큰을 받아온다
	    String accessToken = oauthLogin.getAccessToken(session, code, state);
	    System.out.println("accessToken : " + accessToken);
	    
	    // 3) 액세스 토큰을 이용하여 사용자의 프로필 정보를 받아온다
	    String apiResult = oauthLogin.getUserProfile(accessToken);
	    System.out.println("apiResult : " + apiResult);
	    
	    // 4) ObjectMapper 를 이용하여 자바 객체 타입으로 변환한다
	    OauthUserDTO oauthUser = oauthLogin.getOauthUser(apiResult);
	    oauthUser.setProvider(provider);
	    session.setAttribute("oauthUser", oauthUser); // 사용자 정보를 세션에 저장
	    
	    // 5) 인증 정보를 이용하여 DB에서 먼저 조회한다
	    MemberDTO dtoById = memberService.getMemberById(oauthUser);
	    MemberDTO dtoByEmail = memberService.getMemberByEmail(oauthUser);
	    
	    // 6) DB조회 여부에 따라, 회원가입, 기존 계정 연동, 로그인으로 분기하여 진행한다
	    if(dtoById == null) {       // 연동된 계정이 없음
	        // 이메일이 일치하는 계정이 있음 -> 계정 연동 제안
	        // 이메일이 일치하는 계정도 없음 -> 회원가입
	        mav.addObject("location", dtoByEmail != null ? "/updateId" : "/member/joinBasicMember");
	    } else {                    // 연동된 계정 정보가 있음 -> 사용자 세션 설정
	        session.setAttribute("login", dtoById); // 사용자 정보를 세션에 저장
	        mav.addObject("location", "/"); // 메인 페이지로 리다이렉트
	    }
	    return mav;
	}

	
	@GetMapping("/oauth2_naver")
	public ModelAndView oauth2_naver(String code, String state, HttpSession session) throws IOException, InterruptedException, ExecutionException {
		session.setAttribute("provider", "NAVER");
		return oauth2_intergrated(code, state, session);
	}
	
	@GetMapping("/updateId")
	public void updateNaverId() {}
	
	@PostMapping("/updateId")
	public String updateNaverId(HttpSession session) {
		OauthUserDTO oauthUser = (OauthUserDTO)session.getAttribute("oauthUser");
		int row = memberService.updateId(oauthUser);
		System.out.println(row != 0 ? "성공" : "실패");
		return "redirect:/";
	}
}
