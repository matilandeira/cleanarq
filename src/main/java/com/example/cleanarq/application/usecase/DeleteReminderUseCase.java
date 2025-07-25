package com.example.cleanarq.application.usecase;

import com.example.cleanarq.domain.port.ReminderRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DeleteReminderUseCase {
    private final ReminderRepository reminderRepository;

    public DeleteReminderUseCase(ReminderRepository reminderRepository) {
        this.reminderRepository = reminderRepository;
    }

    public Mono<Void> execute(String id) {
        return reminderRepository.delete(id)
                .doOnSuccess(v -> System.out.println("Reminder deleted successfully: " + id))
                .doOnError(e -> System.err.println("Error deleting reminder: " + e.getMessage()));
    }
}