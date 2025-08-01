package com.example.cleanarq.application.usecase;

import com.example.cleanarq.domain.model.Reminder;
import com.example.cleanarq.domain.port.ReminderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateReminderUseCaseTest {

    @Mock
    private ReminderRepository reminderRepository;

    @InjectMocks
    private CreateReminderUseCase createReminderUseCase;

    private Reminder validReminder;
    private final String validId = "123";
    private final String validMessage = "Test Reminder";
    private final long validTimestamp = System.currentTimeMillis();
    private final String validPlan = "PREMIUM";

    @BeforeEach
    void setUp() {
        validReminder = new Reminder(validId, validMessage, validTimestamp, validPlan);
    }

    @Test
    void execute_withValidSubscriptionPlan_createsReminderSuccessfully() {
        when(reminderRepository.save(any(Reminder.class))).thenReturn(Mono.just(validReminder));

        Mono<Reminder> result = createReminderUseCase.execute(validId, validMessage, validTimestamp, validPlan);

        StepVerifier.create(result)
                .expectNextMatches(reminder ->
                        reminder.getId().equals(validId) &&
                                reminder.getMessage().equals(validMessage) &&
                                reminder.getTimestamp() == validTimestamp &&
                                reminder.getUserSubscriptionPlan().equals(validPlan))
                .verifyComplete();

        verify(reminderRepository, times(1)).save(any(Reminder.class));
    }

    @Test
    void execute_withInvalidSubscriptionPlan_returnsError() {
        // Arrange
        String invalidPlan = "invalid_plan";
        // No creamos una instancia de Reminder aquí, ya que se crea dentro de execute
        // No configuramos el mock de save() porque no debería ser llamado

        // Act
        Mono<Reminder> result = createReminderUseCase.execute(validId, validMessage, validTimestamp, invalidPlan);

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof IllegalArgumentException &&
                                throwable.getMessage().equals("Subscription plan not valid for creating reminders."))
                .verify();

        verify(reminderRepository, never()).save(any(Reminder.class));
    }

    @Test
    void execute_repositoryThrowsError_propagatesError() {
        String errorMessage = "Database error";
        when(reminderRepository.save(any(Reminder.class)))
                .thenReturn(Mono.error(new RuntimeException(errorMessage)));

        Mono<Reminder> result = createReminderUseCase.execute(validId, validMessage, validTimestamp, validPlan);

        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof RuntimeException &&
                                throwable.getMessage().equals(errorMessage))
                .verify();

        verify(reminderRepository, times(1)).save(any(Reminder.class));
    }
}