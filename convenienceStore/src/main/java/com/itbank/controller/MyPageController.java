package com.itbank.controller;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.itbank.model.ChatDTO;
import com.itbank.model.MemberDTO;
import com.itbank.model.MyLocationDTO;
import com.itbank.model.OrdersDTO;
import com.itbank.model.ProductDTO;
import com.itbank.model.ProductReviewDTO;
import com.itbank.model.StorageitemDTO;
import com.itbank.model.StoreDTO;
import com.itbank.model.StoreReviewDTO;
import com.itbank.model.UserCouponDTO;
import com.itbank.service.MyPageService;


@Controller
@RequestMapping("/myPage")
public class MyPageController {

	@Autowired private MyPageService mps;
	
	@GetMapping("/myPageMain")
	public ModelAndView myPageMain(@RequestParam(name = "userid") String userid) {
		ModelAndView mav = new ModelAndView();
		
		List<MyLocationDTO> list = new ArrayList<>();
		
		list = mps.myLocation(userid);
		
		mav.addObject("list", list);
		
		return mav;
		
	}

	
	@GetMapping("/myInfo")
	public ModelAndView myInfo(@RequestParam(name = "userid") String userid) {
		
		ModelAndView mav = new ModelAndView();
		
	
		List<MemberDTO> list = new ArrayList<>();
		
		list = mps.memberList(userid);
		
		mav.addObject("list", list);
		
		return mav;
		
	}
	
	@GetMapping("/myCoupon")
	public  ModelAndView  myCoupon(@RequestParam(name = "userid") String userid) {
		
		ModelAndView mav = new ModelAndView();
		
		List<UserCouponDTO> list = new ArrayList<>();
		
		list = mps.userCouponList(userid);
		
		mav.addObject("list", list);
		
		return mav;
		
		
	}
	
	@GetMapping("/myLikeList")
	public ModelAndView myLikeList(@RequestParam(name = "userid") String userid) {
		ModelAndView mav = new ModelAndView();
		
		List<ProductDTO> list = new ArrayList<>();
		
		list = mps.productLikeList(userid);
		
		mav.addObject("list", list);
		
		return mav;
		
	}
	
	@GetMapping("/myStoreLike")
	public ModelAndView myStoreLike(@RequestParam(name = "userid") String userid) {
		ModelAndView mav = new ModelAndView();
		
		List<StoreDTO> list = new ArrayList<>();
		
		list = mps.StoreLikeList(userid);
		
		mav.addObject("list", list);
		
		return mav;
		
	}
	
	@GetMapping("/myReview")
	public ModelAndView myReview(@RequestParam(name = "userid") String userid) {
		
		ModelAndView mav = new ModelAndView();
		
		List<ProductReviewDTO> list = new ArrayList<>();
		
		list = mps.productReview(userid);
		
		mav.addObject("list", list);
		
		return mav;
		
	}
	
	@GetMapping("/myStoreReview")
	public ModelAndView myStoreReview(@RequestParam(name = "userid") String userid) {
		
		ModelAndView mav = new ModelAndView();
		
		List<StoreReviewDTO> list = new ArrayList<>();
		
		list = mps.storeReview(userid);
		
		mav.addObject("list", list);
		
		return mav;
		
	}
	
	@GetMapping("/myDelete")
	public void myDelete() {}
	
	@PostMapping("/myDelete")
	public String memberDelete(MemberDTO dto) {
		
		int row = mps.deleteMember(dto);
		
		System.out.println(row != 0 ? "회원탈퇴 성공" : "회원탈퇴 실패");
		System.out.println(row);
		return "redirect:/member/logout";
	}
	
	
	@GetMapping("/myShopping")
	public ModelAndView myShopping(@RequestParam(name = "userid") String userid) {
		
		ModelAndView mav = new ModelAndView();
		
		List<OrdersDTO> list = new ArrayList<>();
		
		list = mps.shoppingList(userid);
		
		mav.addObject("list", list);
		
		return mav;
		
		
	}
	
	
	@GetMapping("/myShoppingSave")
	public ModelAndView myShoppingSave(@RequestParam(name = "userid") String userid) {
		ModelAndView mav = new ModelAndView();
		
		List<StorageitemDTO> list = new ArrayList<>();
		
		list = mps.shoppingSaveStatus0(userid);
		
		mav.addObject("list", list);
		
		return mav;
		
	}
	
	@GetMapping("/myShoppingSaveUse")
	public ModelAndView myShoppingSaveUse(@RequestParam(name = "userid") String userid) {
		ModelAndView mav = new ModelAndView();
		
		List<StorageitemDTO> list = new ArrayList<>();
		
		list = mps.shoppingSaveStatus1(userid);
		
		mav.addObject("list", list);
		
		return mav;
		
	}

	
	
	@GetMapping("/myChatList")
	public ModelAndView myChatList(@RequestParam(name = "userid") String userid) {
	ModelAndView mav = new ModelAndView();
	
	List<ChatDTO> list = new ArrayList<>();
	
	list = mps.chatList(userid);
	
	mav.addObject("list", list);
	
	return mav;
	
	}


	
	
}
