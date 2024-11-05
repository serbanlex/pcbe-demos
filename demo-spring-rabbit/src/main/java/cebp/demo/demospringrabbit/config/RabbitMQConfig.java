package cebp.demo.demospringrabbit.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue commandQueue() {
        return new Queue("java_command_queue");
    }
}