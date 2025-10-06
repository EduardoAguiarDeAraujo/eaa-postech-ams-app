package br.eng.eaa.notifications.listener;


import br.eng.eaa.notifications.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AppointmentListener {

    @RabbitListener(queues = RabbitMQConfig.APPOINTMENT_QUEUE)
    public void receiveMessage(String message) {
        // Simula envio de lembrete (em produção, integrar com e-mail/SMS)
        System.out.println("Recebida mensagem: " + message);
        System.out.println("Enviando lembrete ao paciente: 'Sua consulta foi agendada/atualizada. Verifique detalhes.'");
    }
}
