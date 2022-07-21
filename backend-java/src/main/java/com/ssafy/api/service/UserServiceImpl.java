package com.ssafy.api.service;

import com.ssafy.db.entity.UserHistory;
import com.ssafy.db.entity.UserStat;
import com.ssafy.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.db.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 유저 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserStatRepository userStatRepository;

//    @Autowired
//    UserHistoryRepository userHistoryRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User createUser(UserRegisterPostReq userRegisterInfo) {
        User user = User.builder()
                .em(userRegisterInfo.getEm())
                .pwd(passwordEncoder.encode(userRegisterInfo.getPwd()))
                .nnm(userRegisterInfo.getNnm())
                .build();


        userRepository.save(user); //user table에 삽입

        UserStat userStat = UserStat.builder().id(user.getId()).build();
        userStatRepository.save(userStat);

        return user;
    }

    @Override
    public User getUserById(Long id) {
        // 디비에 유저 정보 조회 (userId 를 통한 조회).
        User user = userRepository.findUserById(id).get();
        return user;
    }

    @Override
    public User getUserByEm(String em) {
        User user = userRepository.findUserByEm(em).get();
        return user;
    }

    @Override
    @Transactional
    public User updateUser(Long id, UserRegisterPostReq userRegisterInfo) {
        User user = userRepository.findById(id).get();

        // 비밀번호 암호화
        if (userRegisterInfo.getPwd() != null) {
            user.setPwd(passwordEncoder.encode(userRegisterInfo.getPwd()));
        }
        if (userRegisterInfo.getNnm() != null) {
            user.setNnm(userRegisterInfo.getNnm());
        }
        // 회원 정보 수정일 설정
        user.setModify_dt(LocalDateTime.now());

        userRepository.save(user);
        return user;
    }

    @Override
    public void resignUserById(Long id) {
        User user = userRepository.findById(id).get();

        // user의 leave_dt 변경
        user.setLeave_dt(LocalDateTime.now());
        userRepository.save(user);
    }

    @Override
    public void getUserStat(Long id) {
        System.out.println("-----------");
//        List<UserHistory> user = userHistoryRepository.findUserHistoryByuser_id(id).get();
//        for(UserHistory u : user) System.out.println(u);
        UserStat userStat = userStatRepository.findStatById(id).get();
        System.out.println(userStat);
        for(UserHistory u : userStat.getUserHistoryList()) System.out.println(u);
        System.out.println("-----------");
    }
}
