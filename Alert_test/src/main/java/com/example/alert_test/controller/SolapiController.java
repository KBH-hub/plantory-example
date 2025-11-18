package com.example.alert_test.controller;

import com.example.alert_test.dto.SmsRequest;
import com.example.alert_test.service.SolapiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sms")
public class SolapiController {

    private final SolapiService solapiService;

    @PostMapping("/send")
    public ResponseEntity<?> sendSms(@Validated @RequestBody SmsRequest request) throws Exception {
        if (request.getTo() == null || request.getTo().isBlank()
                || request.getFrom() == null || request.getFrom().isBlank()
                || request.getText() == null || request.getText().isBlank()) {
            return ResponseEntity.badRequest()
                    .body("to, from, text 필수 값");
        }
        return ResponseEntity.ok(solapiService.sendSms(request));
    }
}
