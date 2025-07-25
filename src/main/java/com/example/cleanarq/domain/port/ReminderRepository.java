package com.example.cleanarq.domain.port;

import com.example.cleanarq.domain.model.Reminder;

public interface ReminderRepository {
    void save(Reminder reminder);
    Reminder findById(String id);
    void delete(String id);
}
