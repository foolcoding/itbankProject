package com.itbank.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.itbank.model.ManagerDTO;
import com.itbank.model.MemberDTO;
import com.itbank.model.StoreDTO;

@Repository
public interface MemberRepository {

	@Select("select * from member where userid = #{userid} and userpw = #{userpw}")
	MemberDTO login(MemberDTO dto);

	@Insert("insert into member (userid, userpw, email, nickname, role, pnum)"
			+ "values (#{userid}, #{userpw}, #{email}, #{nickname}, #{role}, #{pnum})")
	int join(MemberDTO dto);


	
	@Select("select count(*) from member where userid = #{userid}")
	int useridDuplicate(String userid);

	@Select("select count(*) from member where nickname = #{nickname}")
	int nicknameDuplicate(String nickname);

	@Select("select * from store")
	List<StoreDTO> storeName();

	@Select("SELECT "
			+ "	   m.idx,"
			+ "    m.userid,"
			+ "    m.name,"
			+ "    m.status,"
			+ "    s.name AS store_name"
			+ " FROM"
			+ "    manager m"
			+ " JOIN"
			+ "    store s ON m.store_idx = s.idx where status = 0")
	List<ManagerDTO> storeMemberList();

	@Insert("insert into member (userid, userpw, email, nickname, role, pnum)"
			+ "values (#{userid}, #{userpw}, #{email}, #{nickname}, #{role}, #{pnum})")
	int storeMemberJoin(MemberDTO dto);

	@Insert("insert into manager(userid, name, store_idx) values (#{userid}, #{name}, #{store_idx})")
	int storeJoin(MemberDTO dto);

	
	@Update("update member set role = 2 where userid = #{userid}")
	int checkMember(String userid);

	@Update("update manager set status = 1 where userid = #{userid}")
	int checkManager(String userid);

	@Delete("delete manager where userid = #{userid}")
	int deleteManager(String userid);

	@Select("select count(*) from member where userid = #{userid}")
	int checkUserid(String userid);

	@Update("update member set userpw = #{userpw} where userid = #{userid}")
	int settingPassword(MemberDTO dto);
	
	@Update("update member set email = #{email} where userid = #{userid}")
	int updateEmail(MemberDTO dto);

	@Update("update member set pnum = #{pnum} where userid = #{userid}")
	int updatePnum(MemberDTO dto);
	



}
