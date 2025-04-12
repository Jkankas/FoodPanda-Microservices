package com.example.foodpanda_microservices_warehouse.filter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Data
@Builder
public class IpRateFilter {


    private static final int RATE_LIMIT = 3;           // 3 requests per minute
    private static final int BLACKLIST_THRESHOLD = 5; // Auto-block after 5 hits


    private final Map<String, Bucket> ipBuckets = new ConcurrentHashMap<>();
    private final Set<String> blacklistedIps = ConcurrentHashMap.newKeySet();
    private final Map<String, AtomicInteger> hitCounter = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public IpRateFilter() {
        // Clear request counts every 5 minutes
        scheduler.scheduleAtFixedRate(hitCounter::clear, 0, 5, TimeUnit.MINUTES);
    }

    private Bucket createNewBucket() {
        return Bucket.builder()
                .addLimit(Bandwidth.classic(RATE_LIMIT, Refill.greedy(RATE_LIMIT, Duration.ofMinutes(1))))
                .build();
    }


    public Bucket getNewBucket(){
        return createNewBucket();
    }

    public int getBlacklistThreshold(){
        return BLACKLIST_THRESHOLD;
    }

    public int getRateLimit(){
        return RATE_LIMIT;
    }

}
