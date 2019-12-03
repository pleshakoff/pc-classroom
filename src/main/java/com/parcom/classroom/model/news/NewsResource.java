package com.parcom.classroom.model.news;


import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NewsResource {

    private final Long id;
    private final String title;
    private final String message;
    private final   String author;
    private final LocalDateTime dateTime;

    public NewsResource(News news) {
        this.id = news.getId();
        this.title = news.getTitle();
        this.message = news.getMessage();
        this.author = news.getUser().getUsername();
        this.dateTime = news.getDateTime();
    }
}