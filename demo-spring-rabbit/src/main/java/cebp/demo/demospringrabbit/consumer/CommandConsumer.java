package cebp.demo.demospringrabbit.consumer;

import cebp.demo.demospringrabbit.model.CreateUserCommand;
import cebp.demo.demospringrabbit.model.DeleteUserCommand;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CommandConsumer {

    private final ObjectMapper objectMapper;

    public CommandConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "java_command_queue")
    public void receiveMessage(String messageJson) {
        try {
            Map<String, Object> message = objectMapper.readValue(messageJson, Map.class);
            String commandType = (String) message.get("command_type");
            Object data = message.get("data");

            if ("CreateUserCommand".equals(commandType)) {
                CreateUserCommand command = objectMapper.convertValue(data, CreateUserCommand.class);
                handleCreateUser(command);
            } else if ("DeleteUserCommand".equals(commandType)) {
                DeleteUserCommand command = objectMapper.convertValue(data, DeleteUserCommand.class);
                handleDeleteUser(command);
            } else {
                System.out.println("Unknown command type: " + commandType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleCreateUser(CreateUserCommand command) {
        System.out.println("RMQ Consumer - Create user: " + command.getUsername() + ", email: " + command.getEmail());
    }

    private void handleDeleteUser(DeleteUserCommand command) {
        System.out.println("RMQ Consumer - Delete user with ID: " + command.getUserId());
    }
}