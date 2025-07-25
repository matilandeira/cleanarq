package com.example.cleanarq.presentation.dto;

import lombok.Data;

@Data
public class CreateReminderRequest {
    private String id;
    private String message;
    private Long timestamp;
    private String userSubscriptionPlan;
}
