package com.example.emailsendconsumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// kafka 에서 메시지를 가져와서, 메시지 정보를 담을 객체
public class EmailSendMessage {
    private String from;
    private String to;
    private String subject;
    private String body;

    // 역직렬화시 파라미터 없는 기본생성자 필요
    public EmailSendMessage() {
    }

    public EmailSendMessage(String from, String to, String subject, String body) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.body = body;
    }

    // 참고) 역직렬화시 getter 필수
    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    // String(Json Format) -> EmailSendMessage 로 변환
    public static EmailSendMessage fromJson(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, EmailSendMessage.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Json Parsing Failed");
        }
    }
}
