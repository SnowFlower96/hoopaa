package com.ssafy.redis;


import com.ssafy.redis.repository.RedisRepository;
import io.openvidu.java.client.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisRepository redisRepository;

    @Test
    public void insertRoom() throws OpenViduJavaClientException, OpenViduHttpException {

    }

}
