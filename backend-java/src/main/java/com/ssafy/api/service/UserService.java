package com.ssafy.api.service;

import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.db.entity.User;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface UserService {
	// Create
	User createUser(UserRegisterPostReq userRegisterInfo);
	// Read
	User getUserById(Long id);
	User getUserByEm(String em);  // 로그인 단계에서만 사용
	// Update
	User updateUser(UserRegisterPostReq userRegisterInfo);
	// Delete
	void deleteUserById(Long id);
}
