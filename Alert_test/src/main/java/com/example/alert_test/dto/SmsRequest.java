package com.example.alert_test.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmsRequest {
    private String to;
    private String from;
    private String text;
}
