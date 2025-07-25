package com.example.cleanarq.application.usecase;

import com.example.cleanarq.domain.model.Reminder;
import com.example.cleanarq.domain.port.ReminderRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GetReminderUseCase {
    private final ReminderRepository reminderRepository;

    public GetReminderUseCase(ReminderRepository reminderRepository) {
        this.reminderRepository = reminderRepository;
    }

    public Mono<Reminder> execute(String id) {
        return reminderRepository.findById(id);
    }
}