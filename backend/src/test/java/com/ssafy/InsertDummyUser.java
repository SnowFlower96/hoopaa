package com.ssafy;

import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.service.UserService;
import com.ssafy.common.util.AES128Util;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class InsertDummyUser {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void test() throws InterruptedException {
        AES128Util aes128Util = new AES128Util("1234567890123456");
        String str = "1";
        String enc = aes128Util.encrypt(str);
        String dec = aes128Util.decrypt(enc);
        System.out.println(str);
        System.out.println(enc);
        System.out.println(dec);
    }

}
