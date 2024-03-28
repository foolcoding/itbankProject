package com.itbank.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.itbank.component.MailComponent;
import com.itbank.model.ManagerDTO;
import com.itbank.model.MemberDTO;
import com.itbank.service.MemberService;
import com.itbank.service.MyPageService;

@RestController
public class MemberRestController {

	@Autowired
	private MemberService ms;
	
	@Autowired private MyPageService mps;
	
	
	@Autowired
	private MailComponent component;
		

    Random random = new Random();
	
    
	
	@GetMapping(value = "/checkUserid", produces = "application/json;charset=UTF-8")
	public String checkUserId(String userid) {
		// 사용자 ID의 중복 여부를 확인하는 서비스 로직 호출
		
		if(userid == "") {
			return "아이디를 입력해주세요";
		}
		
		
		int isDuplicate = ms.isUseridDuplicate(userid);
		
		// 중복 여부에 따라 메시지 반환
		if (isDuplicate >= 1) {
			return "사용 불가능한 아이디입니다";
		} else {
			return "사용 가능한 아이디입니다";
		}
	}

	@GetMapping(value = "/checkNickName", produces = "application/json;charset=UTF-8")
	public String checkNickName(String nickname) {
		
		
		if(nickname == "") {
			return "닉네임을 입력해주세요";
		}
		
		
		int isDuplicate = ms.isNickNameDuplicate(nickname);

		if (isDuplicate >= 1) {
			return "사용 불가능한 닉네임입니다";
		} else {
			return "사용 가능한 닉네임입니다";
		}
	}

	@GetMapping(value = "/storeMemberList", produces = "application/json;charset=UTF-8")
	public String updateMemberStatus(String userid) {

		int row1 = ms.checkMemberUpdate(userid);

		int row2 = ms.checkManagerUpdate(userid);

		if (row1 != 0 && row2 != 0) {
			return userid + "의 가입 승인 성공했습니다.";
		} else {
			return userid + "의 가입 승인 실패했습니다.";
		}

	}

	@GetMapping(value = "/deleteManager", produces = "application/json;charset=UTF-8")
	public String DeleteManager(String userid) {

		int row = ms.deleteMember(userid);

		if (row != 0) {
			return userid + "의 점주 가입을 거부했습니다.";
		} else {
			return userid + "의 가입 거부를 실패했습니다.";
		}

	}
	
	@GetMapping(value = "/managerList", produces = "application/json;charset=UTF-8")
	public List<ManagerDTO> managerList(){
		List<ManagerDTO> list = ms.storeMemberList();
		return list;
	}
	
	
	@GetMapping(value = "/exist", produces = "application/json;charset=UTF-8")
	public String existUserid(String userid) {
		
		int row = ms.checkUseridExist(userid);
		
		if(row != 0) {
			return "계정이 존재합니다.";	
		}
		else {
			return "계정이 존재하지 않습니다.";	
		}
	}
	
	

	
	@PostMapping("/sendMail")
	public int sendMail(@RequestBody HashMap<String, String> param, HttpSession session) {
		
		System.out.println("address : " + param.get("address"));
		
        String sb = "";
        
        for (int i = 0; i < 7; i++) {
            								// 숫자와 대문자 중 무엇을 추가할지 결정합니다.
            if (random.nextBoolean()) {
                							// 랜덤 숫자 추가 (0-9)
                sb += random.nextInt(10);
            } else {
                							// 랜덤 대문자 추가 (A-Z)
                char randomChar = (char) ('A' + random.nextInt(26));
                sb += randomChar;
            }
        }
        
        System.out.println("랜덤 문자열: " + sb.toString());
        
		String authNumber = String.format("%7s", sb);
		
		System.out.println("authNumber : " + authNumber);
		
		session.setAttribute("authNumber", authNumber);
		session.setMaxInactiveInterval(180);
		
		
		param.put("subject", "[GS25] 이메일 인증번호 발송");
		param.put("content", authNumber);
		
		int row = component.sendMimeMessage(param);
		
		System.out.println(row != 0 ? "전송 성공" : "전송 실패");
		
		return row;
	}
	
	
	@GetMapping("/authNumber/{inputNumber}")
	public int authNumber(@PathVariable("inputNumber") String inputNumber, HttpSession session) {
		
		String authNumber = (String) session.getAttribute("authNumber");
		
		int row = 0;
		
		if(inputNumber.equals(authNumber)) {
			row = 1;
		}
		return row;
	}
	
	
	@PostMapping("/resetFinish")
	public int resetFinish(@RequestBody HashMap<String, String> param) {
		
		String userid = param.get("userid");
        String userpw = param.get("userpw");
        
        MemberDTO dto = new MemberDTO();
        
        dto.setUserid(userid);
        dto.setUserpw(userpw);
        
        int row = ms.resetFinish(dto);
        
        return row;
	}
	
	
	@PostMapping("/doubleChecking")
	public int doubleChecking(@RequestBody HashMap<String, String> param) {
		
		String userpw = param.get("userpw");
		String userpwCheck = param.get("doubleCheck");
		
		int row = 0;
		
		if(userpw.equals(userpwCheck)) {
			row = 1;
		}
		else {
			row = 0;
		}
		
		return row;
	}

	
	@PostMapping("/EmailReset")
	public int EmailReset(@RequestBody HashMap<String, String> param) {
		
		String userid = param.get("userid");
		String email = param.get("email");
		
		MemberDTO dto = new MemberDTO();
	        
		dto.setUserid(userid);
	   	dto.setEmail(email);
	        
	   	int row = ms.updateEmail(dto);
	        
		return row;
		
	}
	
	@PostMapping("/pnumReset")
	public int pnumReset(@RequestBody HashMap<String, String> param) {
		
		String userid = param.get("userid");
		String pnum = param.get("pnum");
		
		MemberDTO dto = new MemberDTO();
	        
		dto.setUserid(userid);
	   	dto.setPnum(pnum);
	        
	   	int row = ms.updatePnum(dto);
	        
		return row;
		
	}

	
}
