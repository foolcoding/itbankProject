package com.itbank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itbank.component.HashComponent;
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
import com.itbank.repository.MyPageRepository;

@Service
public class MyPageService {

	@Autowired private MyPageRepository mpr;
	@Autowired private HashComponent hashComponent;
	
	public List<MemberDTO> memberList(String userid) {
		return mpr.selectMemberList(userid);
	}


	public List<UserCouponDTO> userCouponList(String userid) {
		return mpr.userCouponList(userid);
	}


	public List<ProductDTO> productLikeList(String userid) {
		return mpr.productLikeList(userid);
	}


	public List<StoreDTO> StoreLikeList(String userid) {
		return mpr.storeLikeList(userid);
	}


	public List<ProductReviewDTO> productReview(String userid) {
		return mpr.productReview(userid);
	}


	public List<StoreReviewDTO> storeReview(String userid) {
		return mpr.storeReview(userid);
	}


	public int deleteMember(MemberDTO dto) {
		dto.setUserpw(hashComponent.getHash(dto.getUserpw()));
		return mpr.deleteMember(dto);
	}


	public List<OrdersDTO> shoppingList(String userid) {
		return mpr.shoppingList(userid);
	}


	public List<StorageitemDTO> shoppingSaveStatus0(String userid) {
		return mpr.shoppingStatus0(userid);
	}


	public List<StorageitemDTO> shoppingSaveStatus1(String userid) {
		return mpr.shoppingStatus1(userid);
	}


	public List<MyLocationDTO> myLocation(String userid) {
		return mpr.myLocation(userid);
	}


	public List<ChatDTO> faqList(String userid) {
		return mpr.faqboard(userid);
	}


	public List<ChatDTO> chatList(String userid) {
		return mpr.chatList(userid);
	}
	

}
