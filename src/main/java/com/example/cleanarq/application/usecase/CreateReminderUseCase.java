package com.example.cleanarq.application.usecase;

import com.example.cleanarq.domain.model.Reminder;
import com.example.cleanarq.domain.port.ReminderRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateReminderUseCase {
    private final ReminderRepository reminderRepository;

    public CreateReminderUseCase(ReminderRepository reminderRepository) {
        this.reminderRepository = reminderRepository;
    }

    public void execute(String id, String message, long timestamp, String userSubscriptionPlan) {
        Reminder newReminder = new Reminder(id, message, timestamp, userSubscriptionPlan);
        if (newReminder.canCreateReminder()) {
            reminderRepository.save(newReminder);
            System.out.println("Reminder created: " + message);
        } else {
            System.out.println("Couldn't create reminder. Invalid user subscription plan.");
        }
    }
}
