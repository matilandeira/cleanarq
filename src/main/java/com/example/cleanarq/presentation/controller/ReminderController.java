package com.example.cleanarq.presentation.controller;

import com.example.cleanarq.application.usecase.CreateReminderUseCase;
import com.example.cleanarq.application.usecase.DeleteReminderUseCase;
import com.example.cleanarq.application.usecase.GetReminderUseCase;
import com.example.cleanarq.presentation.dto.CreateReminderRequest;
import com.example.cleanarq.presentation.dto.ReminderResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/reminders")
public class ReminderController {

    private final CreateReminderUseCase createReminderUseCase;
    private final DeleteReminderUseCase deleteReminderUseCase;
    private final GetReminderUseCase getReminderUseCase;

    public ReminderController(CreateReminderUseCase createReminderUseCase,
                              DeleteReminderUseCase deleteReminderUseCase,
                              GetReminderUseCase getReminderUseCase) {
        this.createReminderUseCase = createReminderUseCase;
        this.deleteReminderUseCase = deleteReminderUseCase;
        this.getReminderUseCase = getReminderUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Indica que el estado de respuesta predeterminado es 201 Created
    public Mono<ResponseEntity<String>> createReminder(@RequestBody CreateReminderRequest request) {
        // Llamamos al caso de uso, que devuelve un Mono<Reminder>.
        // Usamos .map() para transformar el Mono<Reminder> a un Mono<ResponseEntity<String>> en caso de éxito.
        return createReminderUseCase.execute(
                        request.getId(),
                        request.getMessage(),
                        request.getTimestamp(),
                        request.getUserSubscriptionPlan()
                )
                .map(reminder -> ResponseEntity.status(HttpStatus.CREATED).body("Reminder created successfully."))
                // .onErrorResume() para manejar errores de forma reactiva
                .onErrorResume(IllegalArgumentException.class, e ->
                        Mono.just(ResponseEntity.badRequest().body("Error creating reminder: " + e.getMessage()))
                )
                .onErrorResume(Exception.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage()))
                );
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ReminderResponse>> getReminder(@PathVariable String id) {
        return getReminderUseCase.execute(id)
                .map(reminder -> new ReminderResponse(
                        reminder.getId(),
                        reminder.getMessage(),
                        reminder.getTimestamp(),
                        reminder.getUserSubscriptionPlan()
                ))
                .map(reminderResponse -> ResponseEntity.ok(reminderResponse)) // 200 OK
                .defaultIfEmpty(ResponseEntity.notFound().build()); // 404 Not Found if Mono is empty
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Indica que el estado de respuesta predeterminado es 204 No Content
    public Mono<Void> deleteReminder(@PathVariable String id) {
        // deleteUseCase devuelve Mono<Void>, que es lo que el endpoint espera
        return deleteReminderUseCase.execute(id)
                .onErrorResume(Exception.class, e -> {
                    System.err.println("Error deleting reminder: " + e.getMessage());
                    // Puedes lanzar una excepción específica o devolver un Mono.error
                    return Mono.error(new RuntimeException("Failed to delete reminder."));
                });
    }
}