package com.itbank.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itbank.model.ManagerDTO;
import com.itbank.model.MemberDTO;
import com.itbank.model.OauthUserDTO;
import com.itbank.model.StoreDTO;
import com.itbank.oauth2.NaverLogin;
import com.itbank.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Autowired private MemberService ms;
	@Autowired private NaverLogin naverLogin;
	
	
	@GetMapping("/login")
	public ModelAndView loginNaver(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		// 1) 각 서비스의 로그인 페이지로 이동하는 주소 생성
		
		mav.addObject("naverURL", naverLogin.getAuthorizationUrl(session));
		
		return mav;
	}
	
	@PostMapping("/login")
	public String login(HttpSession session, MemberDTO dto) {
		MemberDTO login = ms.login(dto);
		session.setAttribute("login", login);
		// alert 띄우거나 마음대루
		return "redirect:/";
	}
	
	@GetMapping("/join")
	public void join() {}
	
	@GetMapping("/joinBasicMember")
	public void joinBasic() {}
	
	@PostMapping("/joinBasicMember")
	public String joinBasic(MemberDTO dto, HttpSession session) {
		OauthUserDTO oauthUser = (OauthUserDTO) session.getAttribute("oauthUser");
	
		if(oauthUser != null) {
			switch(oauthUser.getProvider()) {
			case "NAVER":	dto.setNaver_id(oauthUser.getId()); break;
			}
		}
		int row = ms.join(dto);
		System.out.println(row != 0 ? "성공" : "실패");
		return "redirect:/member/login";
	}
	
	@GetMapping("/joinStoreMember")
	public void joinStoreMember() {}
	
	@PostMapping("/joinStoreMember")
	public String joinStoreMember(MemberDTO dto, RedirectAttributes rttr) {
		
		int row = ms.joinStoreMember(dto);
		System.out.println(row != 1 ? "가입실패" : "가입성공");
		
		rttr.addFlashAttribute("dto", dto);

		return "redirect:/member/storeFormSubmit";
	}
	
	@GetMapping("/storeFormSubmit")
	public ModelAndView storeFormSubmit() {
		ModelAndView mav = new ModelAndView("/member/storeFormSubmit");
		
		List<StoreDTO> list = new ArrayList<>();
		
		list = ms.StoreName();
		
		mav.addObject("list", list);
		
		return mav;
	}
	
	@PostMapping("/storeFormSubmit")
	public String storeFormSubmit(MemberDTO dto) {
		int row1 = ms.joinManager(dto);
		
		System.out.println(row1 != 0 ? "매장회원 가입성공" : "매장회원 가입실패");
		return "redirect:/member/login";
	}
	
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	@GetMapping("/storeMemberList")
	public ModelAndView storeMemberCheck() {
		ModelAndView mav = new ModelAndView();
		
		List<ManagerDTO> list = new ArrayList<>();
		
		list = ms.storeMemberList();
		
		mav.addObject("list", list);
		
		return mav;
		
	}

	@GetMapping("/passwordReset")
	public void password() {}
	


}
