package com.example.cleanarq.infrastructure.adapter;

import com.example.cleanarq.domain.model.Reminder;
import com.example.cleanarq.domain.port.ReminderRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class InMemoryReminderRepository implements ReminderRepository {
    private final Map<String, Reminder> reminders = new HashMap<>();

    @Override
    public void save(Reminder reminder) {
        reminders.put(reminder.getId(), reminder);
    }

    @Override
    public Reminder findById(String id) {
        return reminders.get(id);
    }

    @Override
    public void delete(String id) {
        reminders.remove(id);
    }
}
