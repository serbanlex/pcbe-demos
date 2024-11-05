package cebp.demo.demospringrabbit.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteUserCommand extends BaseCommand {
    private int userId;
}