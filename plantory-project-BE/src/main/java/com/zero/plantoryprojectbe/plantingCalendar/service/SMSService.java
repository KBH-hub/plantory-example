package com.zero.plantoryprojectbe.plantingCalendar.service;


import com.zero.plantoryprojectbe.plantingCalendar.dto.SMSRequest;

import java.util.Map;

public interface SMSService {
    Map<String, Object> sendSMS(SMSRequest smsRequest) throws Exception;
}
