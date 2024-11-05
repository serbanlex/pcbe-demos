package cebp.demo.demospringrabbit.producer;

import cebp.demo.demospringrabbit.model.BaseCommand;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CommandProducer {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public CommandProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendCommand(BaseCommand command) {
        Map<String, Object> message = new HashMap<>();
        message.put("command_type", command.getClass().getSimpleName());
        message.put("data", command);

        try {
            String messageJson = objectMapper.writeValueAsString(message);
            rabbitTemplate.convertAndSend("java_command_queue", messageJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}