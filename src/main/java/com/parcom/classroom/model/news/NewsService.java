package com.parcom.classroom.model.news;

import com.parcom.classroom.model.group.GroupService;
import com.parcom.classroom.model.user.UserService;
import com.parcom.classroom.security.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final GroupService groupService;
    private final UserService userService;

    List<NewsResource> getNews() {
        return newsRepository.getNews(UserUtils.getIdGroup()).stream().map(NewsResource::new).collect(Collectors.toList());
    }

    private News getById(@NotNull Long idNews) {
        return newsRepository.findById(idNews).orElseThrow(EntityNotFoundException::new);
    }


    @Secured({"ROLE_ADMIN", "ROLE_MEMBER"})
    NewsResource create(NewsDto newsDto) {
        final News news = newsRepository.save(
                News.builder().
                        dateTime(LocalDateTime.now()).
                        title(newsDto.getTitle()).
                        message(newsDto.getMessage()).
                        group(groupService.getCurrentGroup()).
                        user(userService.getCurrentUser()).
                        build()
        );
        return new NewsResource(news);
    }

    @Secured({"ROLE_ADMIN", "ROLE_MEMBER"})
    NewsResource update(Long id, NewsDto newsDto) {
        News news = getById(id);
        news.setTitle(newsDto.getTitle());
        news.setMessage(newsDto.getMessage());
        return new NewsResource(newsRepository.save(
                news
        ));
    }

    @Secured({"ROLE_ADMIN", "ROLE_MEMBER"})
    void delete(Long id) {
        newsRepository.deleteById(id);
    }


}
