package com.itbank.repository;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import com.itbank.model.CartDTO;

@Repository
public interface PaymentRepository {

	@Select("select C.*, P.name, P.price, P.image, P.event from cart C"
			+ "    join product P"
			+ "        on P.idx = C.product_idx"
			+ "            where C.userid = #{userid}"
			+ "                order by P.idx desc")
	List<CartDTO> cartList(String userid);

	@Select("select name from store where idx = #{store_idx}")
	String getStoreName(int store_idx);

	@Delete("delete cart where idx = #{idx}")
	int deleteCart(int idx);

	@Delete("delete cart where idx = #{idx}")
	void removeCart(int idx);

	@Select(" select C.*, P.price from cart C "
			+ " join product P "
			+ " on C.product_idx = P.idx "
			+ " where C.idx = #{idx} ")
	CartDTO selectCart(int idx);

	@Insert(" insert into orders (idx, paymentKey, userid, store_idx, pickupCode, amount) "
			+ " values(#{orderId}, #{paymentKey}, #{userid}, #{store_idx}, #{pickupCode}, #{amount}) ")
	void insertOrders(HashMap<String, Object> map);

	@Insert(" insert into orderdetail (idx, orders_idx, product_idx, cnt, price) "
			+ " values (#{orderdetail_idx}, #{orderId}, #{product_idx}, #{cnt}, #{price}) ")
	void insertOrderdetail(HashMap<String, Object> map);

	@Insert(" insert into storageitem (orderdetail_idx, userid, totalcount, rescount) "
			+ " values (#{orderdetail_idx}, #{userid}, #{cnt}, #{rescount}) ")
	void insertStorage(HashMap<String, Object> map);

	@Update(" update inventory set cnt = cnt - #{cnt} "
			+ " where store_idx = #{store_idx} and product_idx = #{product_idx} "
			+ "   and cnt >= #{cnt} ")
	int updateInventory(HashMap<String, Object> map);

	@Select(" select name from store where idx = #{store_idx} ")
	String selectStoreName(int store_idx);
	
}
