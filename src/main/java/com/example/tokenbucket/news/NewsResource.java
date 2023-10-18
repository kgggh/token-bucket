package com.example.tokenbucket.news;

import lombok.Data;

@Data
public class NewsResource {
    private Long id;
    private String title;
    private String contents;

    public NewsResource(News news) {
        this.id = news.getId();
        this.title = news.getTitle();
        this.contents = news.getTitle();
    }
}
