package com.example.tokenbucket.news;

import com.example.tokenbucket.ratelimit.LocalTokenBucketRateLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;
    private final LocalTokenBucketRateLimiter localTokenBucketRateLimiter;

    private static final String GET_API_KEY = "rate-limit:tester:GET:/news/{id}";
    private static final String GET_ALL_API_KEY = "rate-limit:tester:GET:/news";

    @GetMapping("/news/{id}")
    public ResponseEntity<Object> getNewsById(@PathVariable Long id) {
        if(!localTokenBucketRateLimiter.hasKey(GET_API_KEY)) {
            localTokenBucketRateLimiter.setRateLimit(GET_API_KEY , 10, 10000);
        }

        if(localTokenBucketRateLimiter.allowRequest(GET_API_KEY)) {
            return ResponseEntity
                .status(429)
                .body("""
                    {
                    	"error": "Too Many Requests",
                    	"message": "You have exhausted your API Request Quota"
                    }
                    """);
        }

        return ResponseEntity.ok(newsService.getNewsById(id));
    }

    @GetMapping("/news")
    public ResponseEntity<Object> getNews() {
        if(!localTokenBucketRateLimiter.hasKey(GET_ALL_API_KEY)) {
            localTokenBucketRateLimiter.setRateLimit(GET_ALL_API_KEY, 5, 5000);
        }

        if(localTokenBucketRateLimiter.allowRequest(GET_ALL_API_KEY)) {
            return ResponseEntity
                .status(429)
                .body("""
                    {
                    	"error": "Too Many Requests",
                    	"message": "You have exhausted your API Request Quota"
                    }
                    """);
        }

        return ResponseEntity.ok(newsService.getNews());
    }
}
