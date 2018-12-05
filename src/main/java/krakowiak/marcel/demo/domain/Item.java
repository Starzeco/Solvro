package krakowiak.marcel.demo.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
public class Item {
    private String name;
    private boolean checked;

    @ManyToOne
    private CheckList owner;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public Item(){
        name="deafault";
        checked=false;
        owner=null;
    }


    public Item(String name,boolean checked){
        this.name=name;
        this.checked=checked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(name, item.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }


}
