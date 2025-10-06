package br.eng.eaa.scheduling.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String APPOINTMENT_QUEUE = "appointment.notifications";

    @Bean
    public Queue appointmentQueue() {
        return new Queue(APPOINTMENT_QUEUE, false);
    }
}