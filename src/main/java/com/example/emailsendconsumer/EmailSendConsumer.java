package com.example.emailsendconsumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

// kafka 로 부터 메시지를 받아와서 처리하는 Consumer 로직
@Service
public class EmailSendConsumer {

    @KafkaListener( // kafka 로 부터 메시지를 읽어올때, 애노테이션 기반으로 읽어올 수 있도록 지원 ( kafka 에 있는 email.send 라는 토픽에 메시지가 들어오는지 안들어오는지 주기적으로 listen 해주겠다. )
            topics = "email.send",
            groupId = "email-send-group" // 컨슈머 그룹을 활용해서 메시지를 읽기 때문에, offset 을 활용할 수 있다.
    ) // email-send-group 이라는 컨슈머 그룹으로 email.send 토픽의 메시지를 읽어들이겠다. ( email-send-group 이 kafka 에 기존에 생성된 컨슈머 그룹에 없다면, 해당 컨슈머 그룹을 생성해서 메시지를 읽어들인다. 만약 이미 있다면, 기존 생성된 컨슈머 그룹을 활용해서 메시지를 읽는다. )
    @RetryableTopic(
            attempts = "5", // 총 시도 가능 횟수 (최초 시도 포함)
            backoff = @Backoff(delay = 1000, multiplier = 2) // 재시도를 하는 간격 설정 ( 1초 단위로 재시도 하는데, 그 다음번 재시도는 *2배 이후에 시도하겠다. -> ex. 첫 재시도는 1초후, 두번째 재시도는 2초후, 세번째 재시도는 4초후, 네번째 재시도는 8초후, ... )
    )
    public void consume(String message) {
        System.out.println("Kafka로 부터 받아온 메시지: " + message);

        EmailSendMessage emailSendMessage = EmailSendMessage.fromJson(message);

        // 실제 이메일 발송 로직
        // ... 생략 ...
        if (emailSendMessage.getTo().equals("fail")) {
            System.out.println("잘못된 이메일 주소로 인해 발송 실패");
            throw new RuntimeException("잘못된 이메일 주소로 인해 발송 실패");
        }

        System.out.println("이메일 발송 완료");
    }
}
