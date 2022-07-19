package com.ssafy.api.service;

import com.ssafy.db.entity.UserStat;
import com.ssafy.db.repository.UserStatRepository;
import com.ssafy.db.repository.UserStatRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;
import com.ssafy.db.repository.UserRepositorySupport;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	UserStatRepository userStatRepository;
	
	@Autowired
	UserRepositorySupport userRepositorySupport;

	@Autowired
	UserStatRepositorySupport userStatRepositorySupport;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public User createUser(UserRegisterPostReq userRegisterInfo) {
		User user = new User();
		UserStat userStat = new UserStat();
		user.setEm(userRegisterInfo.getEm());
		// 보안을 위해서 유저 패스워드 암호화 하여 디비에 저장.
		user.setPwd(passwordEncoder.encode(userRegisterInfo.getPwd()));
		user.setNnm(userRegisterInfo.getNmn());

		userRepository.save(user); //user table에 삽입

		user = getUserByEm(user.getEm()); //user의 id 받아오기

		userStat.setId(user.getId());

		userStatRepository.save(userStat);

		return user;
	}

	@Override
	public User getUserByEm(String em) {
		// 디비에 유저 정보 조회 (userId 를 통한 조회).
		User user = userRepositorySupport.findUserByEm(em).get();
		return user;
	}
}
