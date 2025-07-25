package com.example.cleanarq.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ReminderResponse {
    private String id;
    private String message;
    private Long timestamp;
    private String userSubscriptionPlan;
}
