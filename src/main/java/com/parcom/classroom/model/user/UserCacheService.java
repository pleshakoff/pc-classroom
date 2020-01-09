package com.parcom.classroom.model.user;

import org.springframework.scheduling.annotation.Async;

public interface UserCacheService {
    @Async
    void resetUserCache(Long id);
}
