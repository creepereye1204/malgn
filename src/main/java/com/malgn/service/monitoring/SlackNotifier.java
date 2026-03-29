package com.malgn.service.monitoring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class SlackNotifier {

    @Value("${slack.webhook-url}")
    private String webhookUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public void send(String message) {
        if (webhookUrl == null || webhookUrl.contains("YOUR/WEBHOOK/URL")) {
            log.warn("슬랙 웹훅 URL이 설정되지 않았거나 기본값입니다: " + webhookUrl);
            return;
        }

        Map<String, Object> payload = new HashMap<>();
        payload.put("text", message);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);
        try {
            restTemplate.postForEntity(webhookUrl, entity, String.class);
            log.info("슬랙 메시지 전송 성공: " + message);
        } catch (Exception e) {
            log.error("슬랙 메시지 전송 실패: " + e.getMessage());
            log.error("대상 URL: " + webhookUrl);
        }
    }
}
