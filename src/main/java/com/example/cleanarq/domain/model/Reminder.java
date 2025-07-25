package com.example.cleanarq.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Reminder {
    private String id;
    private String message;
    private long timestamp;
    private String userSubscriptionPlan;

    // Regla de negocio: Un recordatorio puede ser creado si el plan de suscripción lo permite
    public boolean canCreateReminder() {
        return "premium".equalsIgnoreCase(userSubscriptionPlan) || "basic".equalsIgnoreCase(userSubscriptionPlan);
    }
}
