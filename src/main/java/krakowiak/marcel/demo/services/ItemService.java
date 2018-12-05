package krakowiak.marcel.demo.services;

import krakowiak.marcel.demo.commands.ItemCommand;
import krakowiak.marcel.demo.domain.Item;

import java.util.Set;

public interface ItemService {

    Set<ItemCommand> findSetOfItemsByCheckListName(String name);
    void updateItemCommand(ItemCommand command,String checkListName);
    ItemCommand saveCommand(ItemCommand command,String checkListName);

    void deleteItemCommandById(Long id,String checkListName);


}
