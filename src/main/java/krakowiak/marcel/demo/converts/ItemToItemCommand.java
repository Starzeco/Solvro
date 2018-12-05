package krakowiak.marcel.demo.converts;


import krakowiak.marcel.demo.commands.ItemCommand;

import krakowiak.marcel.demo.domain.Item;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ItemToItemCommand implements Converter<Item,ItemCommand> {

    final private CheckListToCheckListCommand checkListToCheckListCommand;

    public ItemToItemCommand(CheckListToCheckListCommand checkListToCheckListCommand){
        this.checkListToCheckListCommand=checkListToCheckListCommand;
    }

    @Override
    @Nullable
    @Synchronized
    public ItemCommand convert(Item item) {
        if(item==null){
            return null;
        }
        final ItemCommand itemCommand=new ItemCommand();
        itemCommand.setChecked(item.isChecked());
        itemCommand.setName(item.getName());
        itemCommand.setId(item.getId());

        itemCommand.setOwner(checkListToCheckListCommand.convert(item.getOwner()));

        return itemCommand;
    }
}
