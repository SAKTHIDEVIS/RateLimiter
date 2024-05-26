package com.test.ratelimiter.service;

import com.test.ratelimiter.exception.RateLimitExceedException;
import com.test.ratelimiter.model.RateLimiterConfig;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@Component
public class RateLimiter {

    @Autowired
    Environment environment;

    private RateLimiterConfig config;
    private Map<String, Map<String, Deque<Long>>> requestLogs;

    @PostConstruct
    public void init() {
        config = new RateLimiterConfig(environment.getProperty("rate.limit.default.limit", Integer.class, 10),
                environment.getProperty("rate.limit.window.size", Integer.class, 60));
        requestLogs = new HashMap<>();
    }

    private void cleanUpOldRequests(String user, String api) {
        long currentTime = System.currentTimeMillis() / 1000;
        int windowSize = config.getWindowSizeInSeconds();
        Deque<Long> timestamps = requestLogs.getOrDefault(user, new HashMap<>())
                .getOrDefault(api, new LinkedList<>());

        while (!timestamps.isEmpty() && timestamps.peekFirst() <= currentTime - windowSize) {
            timestamps.pollFirst();
        }
    }

    public Integer addApiLimit(String api, Integer limit) {
        return config.setApiLimit(api, limit);
    }

    public boolean addRequest(String user, String api) {
        cleanUpOldRequests(user, api);
        int limit = config.getLimit(api);

        requestLogs.putIfAbsent(user, new HashMap<>());
        requestLogs.get(user).putIfAbsent(api, new LinkedList<>());
        Deque<Long> timestamps = requestLogs.get(user).get(api);

        if (timestamps.size() < limit) {
            timestamps.addLast(System.currentTimeMillis() / 1000);
            return true;
        } else {
            throw new RateLimitExceedException("Rate limit exceeds for the given user " + user);
        }
    }
}
