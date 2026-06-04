package com.itbank.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.itbank.component.MailComponent;
import com.itbank.model.CartDTO;
import com.itbank.model.MemberDTO;
import com.itbank.repository.PaymentRepository;

@Service
public class PaymentService {

	@Autowired private PaymentRepository pr;
	@Autowired private MailComponent mc;
	@Autowired private OrderService orderService;

	public List<CartDTO> cartList(String userid) {
		return pr.cartList(userid);
	}

	public String getStoreName(int store_idx) {
		return pr.getStoreName(store_idx);
	}

	public int deleteCart(int idx) {
		return pr.deleteCart(idx);
	}
	
	public void removeCart(int[] idx) {
		for(int i = 0; i < idx.length; i++) {
			pr.removeCart(idx[i]);
		}
	}

	public List<CartDTO> selectCartList(int[] idx) {
		List<CartDTO> cart = new ArrayList<>();
		for(int i = 0; i < idx.length; i++) {
			CartDTO dto = pr.selectCart(idx[i]);
			cart.add(dto);
		}
		return cart;
	}

	public void setOrderInfo(String userid,
							String paymentKey, 
							String orderId, 
							int amount,
							int[] idx, 
							int[] storageCnt, 
							MemberDTO member, 
							String orderName, 
							String method, int couponIdx) {
		// 상품의 price가 포함된 cartList
		List<CartDTO> cart = selectCartList(idx);

		// 주문 확정(주문 헤더/상세 + 재고 차감 + 픽업물품)을 하나의 트랜잭션으로 처리한다.
		// 한 단계라도 실패하면 OrderService 안에서 전체 롤백되고, 재고 부족 시 OutOfStockException 이 전파된다.
		int store_idx = cart.get(0).getStore_idx();
		String pickupCode = UUID.randomUUID().toString().substring(0, 8);

		orderService.placeOrder(userid, paymentKey, orderId, amount, store_idx, pickupCode, cart, storageCnt, couponIdx, idx);
		
        // 결제정보 이메일 보내기
        HashMap<String, Object> param = new HashMap<String, Object>();
        String email = member.getEmail();
        String storeName = pr.selectStoreName(store_idx);
        
        // 현재 날짜
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String sysdate = formatter.format(now);
        
        param.put("address", email);
        param.put("subject", "[GS25] 결제 내역 안내");
        param.put("orderName", orderName);
        param.put("sysdate", sysdate);
        param.put("amount", amount);
        param.put("method", method);
        param.put("storeName", storeName);
        
        int row = mc.sendPayMentMessage(param);
	}
	
}
