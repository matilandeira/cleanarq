package com.example.cleanarq.domain.port;

import com.example.cleanarq.domain.model.Reminder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReminderRepository {
    Mono<Reminder> save(Reminder reminder);
    Mono<Reminder> findById(String id);
    Mono<Void> delete(String id);
    Flux<Reminder> findAll();
}
