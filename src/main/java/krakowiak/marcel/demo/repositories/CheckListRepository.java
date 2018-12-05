package krakowiak.marcel.demo.repositories;

import krakowiak.marcel.demo.commands.CheckListCommand;
import krakowiak.marcel.demo.domain.CheckList;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CheckListRepository extends CrudRepository<CheckList,Long> {
    void deleteByName(String name);
    boolean existsByName(String name);
    Optional<CheckList> findByName(String name);


}
