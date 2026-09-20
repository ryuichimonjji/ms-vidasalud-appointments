package cl.duoc.vidasalud.appointments.dto;

import jakarta.validation.constraints.NotNull;
import cl.duoc.vidasalud.appointments.model.AppointmentStatus;

public class StatusUpdateRequest {

    @NotNull
    private AppointmentStatus status;

    public AppointmentStatus getStatus() { return status; }
    public void setStatus(AppointmentStatus status) { this.status = status; }
}