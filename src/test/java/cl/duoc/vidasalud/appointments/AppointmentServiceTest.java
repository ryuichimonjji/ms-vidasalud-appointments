package cl.duoc.vidasalud.appointments;

import cl.duoc.vidasalud.appointments.event.AppointmentEventPublisher;
import cl.duoc.vidasalud.appointments.model.Appointment;
import cl.duoc.vidasalud.appointments.model.AppointmentStatus;
import cl.duoc.vidasalud.appointments.repository.AppointmentRepository;
import cl.duoc.vidasalud.appointments.service.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppointmentServiceTest {

    private AppointmentRepository repository;
    private AppointmentEventPublisher eventPublisher;
    private AppointmentService service;

    @BeforeEach
    void setUp() {
        repository = mock(AppointmentRepository.class);
        eventPublisher = mock(AppointmentEventPublisher.class);
        service = new AppointmentService(repository, eventPublisher);
    }

    @Test
    void noPermiteEnAtencionSinConfirmar() {
        Appointment appointment = new Appointment();
        appointment.setId(1L);
        appointment.setStatus(AppointmentStatus.SOLICITADA);
        when(repository.findById(1L)).thenReturn(Optional.of(appointment));

        var ex = assertThrows(IllegalStateException.class,
            () -> service.changeStatus(1L, AppointmentStatus.EN_ATENCION));

        assertTrue(ex.getMessage().contains("CONFIRMADA"));
        verify(eventPublisher, never()).publishStatusChanged(any());
    }

    @Test
    void cambioValidoActualizaYPublicaEvento() {
        Appointment appointment = new Appointment();
        appointment.setId(2L);
        appointment.setStatus(AppointmentStatus.SOLICITADA);
        when(repository.findById(2L)).thenReturn(Optional.of(appointment));
        when(repository.save(any(Appointment.class))).thenAnswer(inv -> inv.getArgument(0));

        Appointment result = service.changeStatus(2L, AppointmentStatus.CONFIRMADA);

        assertEquals(AppointmentStatus.CONFIRMADA, result.getStatus());
        verify(eventPublisher).publishStatusChanged(result);
    }
}