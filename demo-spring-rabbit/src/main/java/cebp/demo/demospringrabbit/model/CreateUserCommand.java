package cebp.demo.demospringrabbit.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserCommand extends BaseCommand {

    private String username;
    private String email;

    // Lombok generates constructors, getters, setters, etc.
}