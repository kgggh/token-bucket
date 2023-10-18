package com.example.tokenbucket.ratelimit;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 *
 * 싱글 노드환경에서 in-memory map 사용시 example
 * 분산환경일때 레디스를 활용한 분산캐시 적용도 가능
 *
 */
@Slf4j
@Component
public class LocalTokenBucketRateLimiter implements RateLimiter {
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    @Override
    public boolean hasKey(String key) {
        return this.buckets.containsKey(key);
    }

    @Override
    public boolean allowRequest(String key) {
        Bucket bucket = buckets.get(key);

        log.info("[{}] 남은 토큰 - {}", key, bucket.getAvailableTokens());
        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);

        return probe.isConsumed();
    }

    @Override
    public int getRemainingLimit(String key) {
        Bucket bucket = buckets.get(key);

        return (int) bucket.getAvailableTokens();
    }

    @Override
    public void setRateLimit(String key, int limit, int duration) {
        Bucket oldBucket = buckets.get(key);
        Bucket newBucket = Bucket.builder()
            .addLimit(
                bandwidthBuilderCapacityStage -> bandwidthBuilderCapacityStage
                    .capacity(limit)
                    .refillIntervally(limit, Duration.ofMillis(duration))
            )
            .build();

        if (oldBucket != null) {
            oldBucket.tryConsumeAsMuchAsPossible(oldBucket.getAvailableTokens());
        }

        buckets.put(key, newBucket);
    }
}
