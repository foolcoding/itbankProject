package com.itbank.controller;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.itbank.exception.OutOfStockException;
import com.itbank.model.CartDTO;
import com.itbank.model.MemberDTO;
import com.itbank.model.UserCouponDTO;
import com.itbank.service.CouponService;
import com.itbank.service.PaymentService;
import com.itbank.service.TossPaymentService;

@Controller
@RequestMapping("/payment")
public class PaymentController {
	
	@Autowired private PaymentService ps;
	@Autowired private TossPaymentService tps;
	@Autowired private CouponService cs;

	@GetMapping("/cart")
	public ModelAndView cart(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		MemberDTO login = (MemberDTO) session.getAttribute("login");
		String userid = login.getUserid();
		List<CartDTO> list = ps.cartList(userid);
		if(!list.isEmpty()) {
			CartDTO dto = list.get(0);
			int store_idx = dto.getStore_idx();
			String storeName = ps.getStoreName(dto.getStore_idx());
			mav.addObject("storeName", storeName);
			mav.addObject("store_idx", store_idx);
		}
		List<UserCouponDTO> userCouponList = cs.getuserCouponList(userid);
		
		mav.addObject("userCouponList", userCouponList);
		mav.addObject("list", list);
		return mav;
	}
	
	@GetMapping("/cart/delete/{idx}")
	public String deleteCart(@PathVariable("idx") int idx) {
		int row = ps.deleteCart(idx);
		
		return "redirect:/payment/cart";
	}
	
	@GetMapping("/success")
	public String processPayment(@RequestParam("paymentKey") String paymentKey,
								@RequestParam("orderId") String orderId,
								@RequestParam("orderName") String orderName,
								@RequestParam("method") String method,
								@RequestParam("amount") int amount,
								@RequestParam("idx") int[] idx,
								@RequestParam("storageCnt") int[] storageCnt,
								@RequestParam("couponIdx") int couponIdx,
								HttpSession session) {
	    try {
	        // 결제 승인 요청 토스페이먼츠에 전송
	        tps.authorizePayment(paymentKey, orderId, amount);

	        // 세선에서 userid 추출
	        MemberDTO member = (MemberDTO) session.getAttribute("login");
	        String userid = member.getUserid();
	        
	        // 결재관련 DB에 데이터 저장, 재고현황 업데이트, 이메일 보내기
	        ps.setOrderInfo(userid, paymentKey, orderId, amount, idx, storageCnt, member, orderName, method);
	        
	        // 결제에 포함된 상품만 장바구니에서 삭제
	        ps.removeCart(idx);
	        
	        // 쿠폰을 사용했다면 사용처리
	        if(couponIdx != 0) {
	        	int row = cs.updateCoupon(couponIdx);
	        }
	        
	        session.setAttribute("count", 0);
	        return "payment/success";

	    } catch (OutOfStockException e) {
	        // 재고 부족: 주문은 롤백되었으므로 실패 페이지로 안내
	        return "redirect:/payment/fail";
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "redirect:/payment/fail";
	    }
	}
	
	@GetMapping("/fail")
	public void fail() {}
	
	
}
