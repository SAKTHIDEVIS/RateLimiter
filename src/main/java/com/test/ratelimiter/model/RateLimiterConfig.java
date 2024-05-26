package com.test.ratelimiter.model;

import java.util.HashMap;
import java.util.Map;

public class RateLimiterConfig {
    private final int defaultLimit;
    private final int windowSizeInSeconds;
    private final Map<String, Integer> apiLimits;

    public RateLimiterConfig(int defaultLimit, int windowSizeInSeconds) {
        this.defaultLimit = defaultLimit;
        this.windowSizeInSeconds = windowSizeInSeconds;
        this.apiLimits = new HashMap<>();
    }

    public Integer setApiLimit(String api, int limit) {
        return apiLimits.put(api, limit);
    }

    public int getLimit(String api) {
        return apiLimits.getOrDefault(api, defaultLimit);
    }

    public int getWindowSizeInSeconds() {
        return windowSizeInSeconds;
    }
}
