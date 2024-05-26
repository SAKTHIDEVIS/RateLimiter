package com.test.ratelimiter.exception;

public class RateLimitExceedException extends RuntimeException {
    public RateLimitExceedException(String message, Throwable cause) {
        super(message, cause);
    }

    public RateLimitExceedException(String message) {
        super(message);
    }
}
