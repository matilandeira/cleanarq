package com.example.cleanarq.presentation.controller;

import com.example.cleanarq.application.usecase.CreateReminderUseCase;
import com.example.cleanarq.application.usecase.DeleteReminderUseCase;
import com.example.cleanarq.application.usecase.GetReminderUseCase;
import com.example.cleanarq.domain.model.Reminder;
import com.example.cleanarq.presentation.dto.CreateReminderRequest;
import com.example.cleanarq.presentation.dto.ReminderResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // Marca esta clase como un controlador REST
@RequestMapping("/api/reminders") // Prefijo para todas las rutas de este controlador
public class ReminderController {

    private final CreateReminderUseCase createReminderUseCase;
    private final DeleteReminderUseCase deleteReminderUseCase;
    private final GetReminderUseCase getReminderUseCase;

    // Inyección de dependencias a través del constructor
    public ReminderController(CreateReminderUseCase createReminderUseCase,
                              DeleteReminderUseCase deleteReminderUseCase,
                              GetReminderUseCase getReminderUseCase) {
        this.createReminderUseCase = createReminderUseCase;
        this.deleteReminderUseCase = deleteReminderUseCase;
        this.getReminderUseCase = getReminderUseCase;
    }

    @PostMapping // Maneja peticiones POST a /api/reminders
    public ResponseEntity<String> createReminder(@RequestBody CreateReminderRequest request) {
        try {
            createReminderUseCase.execute(
                    request.getId(),
                    request.getMessage(),
                    request.getTimestamp(),
                    request.getUserSubscriptionPlan()
            );
            return new ResponseEntity<>("Recordatorio creado exitosamente", HttpStatus.CREATED);
        } catch (Exception e) { // Manejo de errores básico, puedes refinarlo
            return new ResponseEntity<>("Error al crear recordatorio: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}") // Maneja peticiones GET a /api/reminders/{id}
    public ResponseEntity<ReminderResponse> getReminder(@PathVariable String id) {
        Reminder reminder = getReminderUseCase.execute(id);
        if (reminder != null) {
            ReminderResponse response = new ReminderResponse(
                    reminder.getId(),
                    reminder.getMessage(),
                    reminder.getTimestamp(),
                    reminder.getUserSubscriptionPlan()
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}") // Maneja peticiones DELETE a /api/reminders/{id}
    public ResponseEntity<Void> deleteReminder(@PathVariable String id) {
        deleteReminderUseCase.execute(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}