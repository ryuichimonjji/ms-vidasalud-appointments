package cl.duoc.vidasalud.appointments.service;

import cl.duoc.vidasalud.appointments.dto.AppointmentRequest;
import cl.duoc.vidasalud.appointments.model.Appointment;
import cl.duoc.vidasalud.appointments.model.AppointmentStatus;
import cl.duoc.vidasalud.appointments.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AppointmentService {

    private final AppointmentRepository repository;

    private static final Map<AppointmentStatus, List<AppointmentStatus>> VALID_TRANSITIONS = Map.of(
        AppointmentStatus.SOLICITADA, List.of(AppointmentStatus.CONFIRMADA, AppointmentStatus.CANCELADA),
        AppointmentStatus.CONFIRMADA, List.of(AppointmentStatus.EN_ESPERA, AppointmentStatus.CANCELADA),
        AppointmentStatus.EN_ESPERA, List.of(AppointmentStatus.EN_ATENCION, AppointmentStatus.CANCELADA),
        AppointmentStatus.EN_ATENCION, List.of(AppointmentStatus.CERRADA)
    );

    public AppointmentService(AppointmentRepository repository) {
        this.repository = repository;
    }

    public Appointment create(AppointmentRequest request) {
        Appointment appointment = new Appointment();
        appointment.setPatientId(request.getPatientId());
        appointment.setServiceId(request.getServiceId());
        appointment.setStatus(AppointmentStatus.SOLICITADA);
        return repository.save(appointment);
    }

    public Appointment getById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Atencion no encontrada: " + id));
    }

    public List<Appointment> listByStatus(AppointmentStatus status) {
        return status == null ? repository.findAll() : repository.findByStatus(status);
    }

    public Appointment changeStatus(Long id, AppointmentStatus newStatus) {
        Appointment appointment = getById(id);
        List<AppointmentStatus> allowed = VALID_TRANSITIONS.getOrDefault(appointment.getStatus(), List.of());

        if (!allowed.contains(newStatus)) {
            throw new IllegalStateException(
                "No se puede pasar de " + appointment.getStatus() + " a " + newStatus +
                ". La atencion debe estar CONFIRMADA antes de EN_ATENCION.");
        }
        appointment.setStatus(newStatus);
        return repository.save(appointment);
    }
}