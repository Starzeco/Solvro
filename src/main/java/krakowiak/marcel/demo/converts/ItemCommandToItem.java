package krakowiak.marcel.demo.converts;


import krakowiak.marcel.demo.commands.ItemCommand;

import krakowiak.marcel.demo.domain.CheckList;
import krakowiak.marcel.demo.domain.Item;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ItemCommandToItem implements Converter<ItemCommand,Item> {
    final private CheckListCommandToCheckList checkListCommandToCheckList;


    public ItemCommandToItem(CheckListCommandToCheckList checkListCommandToCheckList){
        this.checkListCommandToCheckList=checkListCommandToCheckList;
    }

    @Override
    @Nullable
    @Synchronized
    public Item convert(ItemCommand itemCommand) {
        if(itemCommand==null){
            return null;
        }

        final Item item=new Item();
        item.setChecked(itemCommand.isChecked());
        item.setId(itemCommand.getId());
        item.setName(itemCommand.getName());


        item.setOwner(checkListCommandToCheckList.convert(itemCommand.getOwner()));

        return item;
    }
}
