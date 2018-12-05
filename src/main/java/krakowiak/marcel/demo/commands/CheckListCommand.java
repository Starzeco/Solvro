package krakowiak.marcel.demo.commands;

import krakowiak.marcel.demo.domain.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class CheckListCommand {

    @Min(1)
    @Max(99)
    private String name;


    private Long id;

    private Set<ItemCommand> items=new HashSet<>();


    public CheckListCommand(String name){
        this.name=name;
    }
}
