package com.example.tokenbucket.news;

import jakarta.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class NewsRepository {
    private Map<Long, News> store = new ConcurrentHashMap<>();
    private AtomicLong id = new AtomicLong();

    @PostConstruct
    public void save() {
        for (int i = 0; i <10; i++) {
            long currentId = this.id.incrementAndGet();

            News news = News.builder()
                .id(currentId)
                .title(String.format("%s-%d일자 뉴스", LocalDate.now(), id.get()))
                .contents("오늘의 뉴스는 어떤 재밌는 내용?")
                .build();

            store.put(news.getId(), news);
        }

        log.info("count={}, {}",store.size(), store);
    }

    public Optional<News> findById(Long id) {
        return Optional.ofNullable(this.store.get(id));
    }

    public List<News> findAll() {
        return new ArrayList<>(this.store.values());
    }
}
