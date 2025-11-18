package com.example.alert_test.service;

import com.example.alert_test.auth.SolapiAuth;
import com.example.alert_test.dto.SmsRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class SolapiService {

    @Value("${solapi.api-key}")
    private String apiKey;

    @Value("${solapi.api-secret}")
    private String apiSecret;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Map<String, Object> sendSms(SmsRequest request) throws Exception {

        // 1) URL 설정
        String url = "https://api.solapi.com/messages/v4/send";

        // 2) Authorization 헤더 생성
        String authHeader = SolapiAuth.createAuthHeader(apiKey, apiSecret);

        // 3) HTTP 헤더 구성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", authHeader);

        // 4) HTTP Body 구성: 솔라피 스펙에 맞게 message 객체
        Map<String, Object> body = Map.of(
                "message", Map.of(
                        "to", request.getTo(),
                        "from", request.getFrom(),
                        "text", request.getText()
                )
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        // 5) POST 요청 전송
        ResponseEntity<String> response =
                restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        // 6) 응답 JSON을 Map으로 변환해서 리턴
        return objectMapper.readValue(response.getBody(), Map.class);
    }
}
