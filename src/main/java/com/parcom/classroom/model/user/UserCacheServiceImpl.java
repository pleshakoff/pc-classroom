package com.parcom.classroom.model.user;

import com.parcom.network.Network;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCacheServiceImpl implements UserCacheService {

    private  final Network network;

    @Override
//    @Async
    public void resetUserCache(Long id) {

        network.callDelete("user-cache", "users", "reset", id.toString());
    }
}