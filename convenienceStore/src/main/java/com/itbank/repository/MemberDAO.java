package com.itbank.repository;

import com.itbank.model.MemberDTO;
import com.itbank.model.OauthUserDTO;

public interface MemberDAO {

	
	MemberDTO selectMemberByEmail(OauthUserDTO oauthUser);

	MemberDTO findByOauthId(OauthUserDTO oauthUser);

	int updateId(OauthUserDTO naverUser);

	MemberDTO selectMemberByUserId(String userid);

	MemberDTO loginNaver(OauthUserDTO dto);


}
