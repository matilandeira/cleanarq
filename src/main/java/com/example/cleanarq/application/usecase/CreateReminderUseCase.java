package com.example.cleanarq.application.usecase;

import com.example.cleanarq.domain.model.Reminder;
import com.example.cleanarq.domain.port.ReminderRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service // Para que Spring lo detecte y pueda inyectarlo
public class CreateReminderUseCase {
    private final ReminderRepository reminderRepository;

    public CreateReminderUseCase(ReminderRepository reminderRepository) {
        this.reminderRepository = reminderRepository;
    }

    public Mono<Reminder> execute(String id, String message, long timestamp, String userSubscriptionPlan) {
        Reminder newReminder = new Reminder(id, message, timestamp, userSubscriptionPlan);
        if (newReminder.canCreateReminder()) {
            return reminderRepository.save(newReminder)
                    .doOnSuccess(r -> System.out.println("Reminder created successfully: " + r.getMessage()))
                    .doOnError(e -> System.err.println("Error creating reminder: " + e.getMessage()));
        } else {
            return Mono.error(new IllegalArgumentException("Subscription plan not valid for creating reminders."));
        }
    }
}