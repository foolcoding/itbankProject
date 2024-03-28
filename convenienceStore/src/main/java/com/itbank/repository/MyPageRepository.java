package com.itbank.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

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

@Repository
public interface MyPageRepository {

	@Select("select * from member where userid = #{userid}")
	List<MemberDTO> selectMemberList(String userid);

	@Select("SELECT"
			+ "    c.title,"
			+ "    c.discvalue,"
			+ "    u.issueDate,"
			+ "    u.endDate,"
			+ "    u.usedDate"
			+ " FROM"
			+ "    coupon c"
			+ " JOIN usercoupon u ON c.idx = u.coupon_idx where userid = #{userid}")
	List<UserCouponDTO> userCouponList(String userid);

	
	@Select("SELECT"
			+ "    p.name,"
			+ "    p.price,"
			+ "    p.event"
			+ " FROM"
			+ "    product p"
			+ " JOIN productlike pl ON p.idx = pl.product_idx where userid = #{userid}")
	List<ProductDTO> productLikeList(String userid);

	@Select("SELECT"
			+ "    s.name,"
			+ "    s.address,"
			+ "    s.pnum"
			+ " FROM"
			+ "    store s"
			+ " JOIN storelike sl ON s.idx = sl.store_idx where userid = #{userid}")
	List<StoreDTO> storeLikeList(String userid);

	
	@Select("SELECT"
			+ "    pr.idx,"
			+ "    pr.score,"
			+ "	   pr.reviewDate,"
			+ "	   p.name"
			+ " FROM"
			+ "    product p"
			+ " JOIN productreview pr ON p.idx = pr.product_idx where userid = #{userid}"
			+ "	order by reviewDate desc")
	List<ProductReviewDTO> productReview(String userid);

	
	@Select("SELECT"
			+ "    sr.idx,"
			+ "    sr.score,"
			+ "    sr.reviewDate,"
			+ "	   s.name"
			+ " FROM"
			+ "    store s"
			+ " JOIN storereview sr ON s.idx = sr.store_idx where userid = #{userid}"
			+ "	order by reviewDate desc")
	List<StoreReviewDTO> storeReview(String userid);

	@Delete("delete member where userid = #{userid} and userpw = #{userpw}")
	int deleteMember(MemberDTO dto);

	@Select("SELECT o.pickupcode, o.orderDate, o.pickupTime, o.amount, s.name"
			+ "	FROM orders o"
			+ "	JOIN store s ON o.store_idx = s.idx"
			+ "	where userid = #{userid}"
			+ "	order by o.orderDate desc")
	List<OrdersDTO> shoppingList(String userid);

	
	
	@Select("	SELECT si.totalCount, si.resCount, si.status, p.name"
			+ "	FROM storageItem si"
			+ "	JOIN orderDetail od ON si.orderDetail_idx = od.idx"
			+ "	JOIN product p ON od.product_idx = p.idx"
			+ "	where userid = #{userid} and si.status = 0")
	List<StorageitemDTO> shoppingStatus0(String userid);
	
	
	
	@Select("	SELECT si.totalCount, si.resCount, si.status, p.name"
			+ "	FROM storageItem si"
			+ "	JOIN orderDetail od ON si.orderDetail_idx = od.idx"
			+ "	JOIN product p ON od.product_idx = p.idx"
			+ "	where userid = #{userid} and si.status = 1")
	List<StorageitemDTO> shoppingStatus1(String userid);

	
	@Select("SELECT ml.address, ml.lat, ml.lng"
			+ "	FROM member m"
			+ "	JOIN myLocation ml ON m.idx = ml.member_idx"
			+ "	WHERE m.userid = #{userid}")
	List<MyLocationDTO> myLocation(String userid);

	
	@Select("select * from faqboard where writer = #{userid}")
	List<ChatDTO> faqboard(String userid);

	@Select("select * from chat where userid1 = #{userid} order by chatDate desc")
	List<ChatDTO> chatList(String userid);




	
	
}
