package com.ssafy;

import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.service.UserService;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.UserHistory;
import com.ssafy.db.entity.UserStat;
import com.ssafy.db.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class UserTest {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void test() {
        UserRegisterPostReq userRegisterPostReq = new UserRegisterPostReq();
        userRegisterPostReq.setEm("test");
        userRegisterPostReq.setPwd("test");
        userRegisterPostReq.setNnm("test");
        userService.createUser(userRegisterPostReq);

        userRegisterPostReq = new UserRegisterPostReq();
        userRegisterPostReq.setEm("admin");
        userRegisterPostReq.setPwd("admin");
        userRegisterPostReq.setNnm("admin");
        userService.createUser(userRegisterPostReq);
    }

}
