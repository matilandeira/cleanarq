package com.example.cleanarq.infrastructure.adapter;

import com.example.cleanarq.domain.model.Reminder;
import com.example.cleanarq.domain.port.ReminderRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component // Para que Spring lo detecte y pueda inyectarlo
public class InMemoryReminderRepository implements ReminderRepository {
    private final Map<String, Reminder> reminders = new ConcurrentHashMap<>();

    @Override
    public Mono<Reminder> save(Reminder reminder) {
        // Mono.just() crea un Mono que emite un elemento y luego completa
        return Mono.fromCallable(() -> { // fromCallable para operaciones bloqueantes si las hubiera
            reminders.put(reminder.getId(), reminder);
            return reminder;
        });
    }

    @Override
    public Mono<Reminder> findById(String id) {
        // Mono.justOrEmpty() emite el elemento si no es nulo, o completa vacío si es nulo
        return Mono.justOrEmpty(reminders.get(id));
    }

    @Override
    public Mono<Void> delete(String id) {
        return Mono.fromRunnable(() -> reminders.remove(id)).then(); // then() para ignorar el resultado y completar con Void
    }

    @Override
    public Flux<Reminder> findAll() {
        return Flux.fromIterable(reminders.values());
    }
}