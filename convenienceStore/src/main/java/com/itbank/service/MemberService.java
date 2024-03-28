package com.itbank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itbank.component.HashComponent;
import com.itbank.model.ManagerDTO;
import com.itbank.model.MemberDTO;
import com.itbank.model.OauthUserDTO;
import com.itbank.model.StoreDTO;
import com.itbank.repository.MemberDAO;
import com.itbank.repository.MemberRepository;

@Service
public class MemberService {

	@Autowired private MemberRepository mr;
	@Autowired private MemberDAO dao;
	@Autowired private HashComponent hashComponent;


	public MemberDTO login(MemberDTO dto) {
		dto.setUserpw(hashComponent.getHash(dto.getUserpw()));
		return mr.login(dto);
	}
	
	public int join(MemberDTO dto) {
		dto.setUserpw(hashComponent.getHash(dto.getUserpw()));
		dto.setRole(3);
		return mr.join(dto);
	}

	public int isUseridDuplicate(String userid) {
		return mr.useridDuplicate(userid);
	}

	public int isNickNameDuplicate(String nickname) {
		return mr.nicknameDuplicate(nickname);
	}

	public List<StoreDTO> StoreName() {
		return mr.storeName();
	}

	public List<ManagerDTO> storeMemberList() {
		return mr.storeMemberList();
	}


	public int joinStoreMember(MemberDTO dto) {
		dto.setUserpw(hashComponent.getHash(dto.getUserpw()));
		dto.setRole(3);
		return mr.storeMemberJoin(dto);
	}

	public int joinManager(MemberDTO dto) {
		return mr.storeJoin(dto);
	}

	public int checkMemberUpdate(String userid) {
		return mr.checkMember(userid);
	}

	public int checkManagerUpdate(String userid) {
		return mr.checkManager(userid);
	}

	public int deleteMember(String userid) {
		return mr.deleteManager(userid);
	}

	public int checkUseridExist(String userid) {
		return mr.checkUserid(userid);
	}


	public int resetFinish(MemberDTO dto) {
		dto.setUserpw(hashComponent.getHash(dto.getUserpw()));
		return mr.settingPassword(dto);
	}

	public int updateEmail(MemberDTO dto) {
		return mr.updateEmail(dto);
	}

	public int updatePnum(MemberDTO dto) {
		return mr.updatePnum(dto);
	}

	public MemberDTO getMemberById(OauthUserDTO oauthUser) {
		return dao.findByOauthId(oauthUser);		
	}
	
	public MemberDTO getMemberByEmail(OauthUserDTO email) {
		return dao.selectMemberByEmail(email);
	}

	public int updateId(OauthUserDTO oauthUser) {
		return dao.updateId(oauthUser);
	}


	public MemberDTO loginNaver(OauthUserDTO dto) {
		return dao.loginNaver(dto);
	}




}
