package com.example.tokenbucket.ratelimit;

public interface RateLimiter {
    boolean hasKey(String key);
    boolean allowRequest(String key);

    int getRemainingLimit(String key);

    void setRateLimit(String key, int limit, int duration);
}
