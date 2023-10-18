package com.example.tokenbucket.news;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    
    public NewsResource getNewsById(Long id) {
        News news = newsRepository.findById(id)
            .orElseThrow(
                () -> new IllegalArgumentException(id +" 존재하지 않는 뉴스입니다."));
        
        return new NewsResource(news);
    }
    
    public List<NewsResource> getNews() {
        List<News> users = newsRepository.findAll();
        
        return users.stream()
            .map(NewsResource::new)
            .toList();
    }
}
