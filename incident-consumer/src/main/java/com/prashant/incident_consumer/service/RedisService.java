package com.prashant.incident_consumer.service;

import com.prashant.incident_consumer.model.LogEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final StringRedisTemplate redisTemplate;

    public boolean isDuplicate(String key){
        boolean exists = redisTemplate.hasKey(key);
        if (exists) {
            return true;
        }
        redisTemplate.opsForValue().set(key, "1", Duration.ofMinutes(5));
        return false;
    }

    public String generateKey(LogEvent log) {
        return log.getService()+":"+log.getMessage();
    }
}
