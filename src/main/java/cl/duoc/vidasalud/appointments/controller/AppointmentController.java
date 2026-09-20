package cl.duoc.vidasalud.appointments.controller;

import cl.duoc.vidasalud.appointments.dto.AppointmentRequest;
import cl.duoc.vidasalud.appointments.dto.StatusUpdateRequest;
import cl.duoc.vidasalud.appointments.model.Appointment;
import cl.duoc.vidasalud.appointments.model.AppointmentStatus;
import cl.duoc.vidasalud.appointments.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Appointment> create(@Valid @RequestBody AppointmentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> list(
            @RequestParam(required = false) AppointmentStatus status) {
        return ResponseEntity.ok(service.listByStatus(status));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Appointment> changeStatus(
            @PathVariable Long id,
            @Valid @RequestBody StatusUpdateRequest request) {
        return ResponseEntity.ok(service.changeStatus(id, request.getStatus()));
    }
}