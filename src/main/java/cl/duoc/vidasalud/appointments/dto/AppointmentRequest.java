package cl.duoc.vidasalud.appointments.dto;

import jakarta.validation.constraints.NotBlank;

public class AppointmentRequest {

    @NotBlank
    private String patientId;

    @NotBlank
    private String serviceId;

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public String getServiceId() { return serviceId; }
    public void setServiceId(String serviceId) { this.serviceId = serviceId; }
}