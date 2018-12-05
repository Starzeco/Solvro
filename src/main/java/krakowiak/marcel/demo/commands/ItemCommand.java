package krakowiak.marcel.demo.commands;

import krakowiak.marcel.demo.domain.CheckList;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
@NoArgsConstructor
public class ItemCommand {


    private Long id;

    @Min(1)
    @Max(99)
    private String name;

    private boolean checked;



    private CheckListCommand owner;


    public ItemCommand(Long id, boolean checked) {
        this.id = id;
        this.checked = checked;
    }

    public ItemCommand(@Min(1) @Max(99) String name) {
        checked=false;
        this.name = name;
        owner=null;

    }
}
