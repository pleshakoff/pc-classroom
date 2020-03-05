package com.parcom.classroom.services.sync;


import com.parcom.asyncdto.SyncStudentDto;
import com.parcom.security_client.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;


@Service
@RequiredArgsConstructor
@Slf4j
public class SyncStudentServiceImpl  {

    private final KafkaTemplate<String, SyncStudentDto> notificationDtoKafkaTemplate;

    @Value("${parcom.kafka.topic.students}")
    private String studentsTopic;


    private void sendToKafka(SyncStudentDto notificationDto) {


        Message<SyncStudentDto> message = MessageBuilder
                .withPayload(notificationDto)
                .setHeader(KafkaHeaders.TOPIC, studentsTopic)
                .setHeader(UserUtils.X_AUTH_TOKEN, UserUtils.getToken())
                .build();


        ListenableFuture<SendResult<String, SyncStudentDto>> future =
                notificationDtoKafkaTemplate.send(message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, SyncStudentDto>>() {

            @Override
            public void onSuccess(SendResult<String, SyncStudentDto> result) {
                log.info("Send sync student");
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Send sync student failure id ", ex);
            }
        });
    }


    @TransactionalEventListener
    public void onApplicationEvent(SyncStudentEvent event) {
        sendToKafka(new SyncStudentDto(event.getIdStudent()));
    }
}
