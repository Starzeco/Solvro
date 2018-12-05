package krakowiak.marcel.demo.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
public class CheckList {

    @Column(unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.ALL,mappedBy="owner")
    private Set<Item> items=new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public CheckList(){

    }

    public CheckList(String name,Set<Item> items){
        this.name=name;
        this.items=items;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckList checkList = (CheckList) o;
        return Objects.equals(name, checkList.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }
    public void addItem(Item item){
        items.add(item);
    }
}
