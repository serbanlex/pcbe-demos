package cebp.demo.demospringrabbit.controller;

import cebp.demo.demospringrabbit.model.CreateUserCommand;
import cebp.demo.demospringrabbit.model.DeleteUserCommand;
import cebp.demo.demospringrabbit.producer.CommandProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private final CommandProducer commandProducer;

    public UserController(CommandProducer commandProducer) {
        this.commandProducer = commandProducer;
    }
    @GetMapping("/")
    public ResponseEntity<String> getUser() {
        return ResponseEntity.status(HttpStatus.OK).body("Some user's information");
    }

    @PostMapping("/")
    public ResponseEntity<?> createUser(@RequestBody CreateUserCommand command) {
        commandProducer.sendCommand(command);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "CreateUserCommand sent to queue");
        response.put("username", command.getUsername());
        response.put("email", command.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable int userId) {
        DeleteUserCommand command = new DeleteUserCommand(userId);
        commandProducer.sendCommand(command);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "DeleteUserCommand sent to queue");
        response.put("user_id", command.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}