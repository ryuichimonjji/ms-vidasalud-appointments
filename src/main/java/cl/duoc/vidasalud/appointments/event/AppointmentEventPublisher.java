package cl.duoc.vidasalud.appointments.event;

import cl.duoc.vidasalud.appointments.config.RabbitMQProperties;
import cl.duoc.vidasalud.appointments.model.Appointment;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class AppointmentEventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQProperties properties;

    public AppointmentEventPublisher(RabbitTemplate rabbitTemplate, RabbitMQProperties properties) {
        this.rabbitTemplate = rabbitTemplate;
        this.properties = properties;
    }

    public void publishCreated(Appointment appointment) {
        AppointmentEvent event = toEvent(appointment);
        rabbitTemplate.convertAndSend(
            properties.getExchange(),
            properties.getRoutingKey().getCreated(),
            event
        );
    }

    public void publishStatusChanged(Appointment appointment) {
        AppointmentEvent event = toEvent(appointment);
        rabbitTemplate.convertAndSend(
            properties.getExchange(),
            properties.getRoutingKey().getStatusChanged(),
            event
        );
    }

    private AppointmentEvent toEvent(Appointment appointment) {
        return new AppointmentEvent(
            appointment.getId(),
            appointment.getPatientId(),
            appointment.getServiceId(),
            appointment.getStatus()
        );
    }
}