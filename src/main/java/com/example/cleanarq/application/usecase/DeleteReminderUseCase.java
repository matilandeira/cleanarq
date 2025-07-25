package com.example.cleanarq.application.usecase;

import com.example.cleanarq.domain.port.ReminderRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteReminderUseCase {
    private final ReminderRepository reminderRepository;

    public DeleteReminderUseCase(ReminderRepository reminderRepository) {
        this.reminderRepository = reminderRepository;
    }

    public void execute(String id) {
        reminderRepository.delete(id);
        System.out.println("Recordatorio eliminado: " + id);
    }
}
