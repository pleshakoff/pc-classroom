package com.parcom.classroom.model.news;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NewsRepository extends CrudRepository<News, Long> {

    @Query("select news from News news " +
            "where news.group.id =  :idGroup " +
            "order by news.dateTime desc")
    List<News> getNews(@Param("idGroup") Long idGroup);


}
