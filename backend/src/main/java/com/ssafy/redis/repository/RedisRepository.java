package com.ssafy.redis.repository;

import com.ssafy.redis.entity.VRoom;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisRepository extends CrudRepository<VRoom, String> {
}
