package com.example.emailsendconsumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmailSendDltConsumer {

    @KafkaListener(
            topics = "email.send.dlt", // dlt 토픽에 쌓인 메시지
            groupId = "email-send-dlt-group" // dlt 토픽에 쌓인 메시지를 읽어들이는 컨슈머 그룹의 이름
    )
    public void consume(String message) {

        try {
            // ...로그 시스템에 전송하는 로직은 생략...
            System.out.println("로그 시스템에 전송 : " + message);

            // ...Slack에 알림 발송하는 로직은 생략...
            System.out.println("Slack에 알림 발송");
        } catch (Exception e) {
            // log.error("DLT 처리 실패", e);
        }
    }
}
