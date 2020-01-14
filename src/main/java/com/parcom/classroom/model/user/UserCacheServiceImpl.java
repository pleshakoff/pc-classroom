package com.parcom.classroom.model.user;

import com.parcom.security_client.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserCacheServiceImpl implements UserCacheService {

    private final KafkaTemplate<String, Long> notificationDtoKafkaTemplate;

    @Value("${parcom.kafka.topic.user}")
    private String userTopic;

    @Override
    @Async
    public void resetUserCache(Long id) {
        sendToKafka(id);
    }


    private void sendToKafka(Long idUser) {


        Message<Long> message = MessageBuilder
                .withPayload(idUser)
                .setHeader(KafkaHeaders.TOPIC, userTopic)
                .setHeader(UserUtils.X_AUTH_TOKEN, UserUtils.getToken())
                .build();


        ListenableFuture<SendResult<String, Long>> future =
                notificationDtoKafkaTemplate.send(message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, Long>>() {

            @Override
            public void onSuccess(SendResult<String, Long> result) {
                log.info("Send user cache reset message for user id {}",idUser);
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error(String.format("FAILURE! Send user cache reset message for user id %s.",idUser),ex);
            }
        });
    }



}