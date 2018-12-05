package krakowiak.marcel.demo.converts;


import krakowiak.marcel.demo.commands.CheckListCommand;
import krakowiak.marcel.demo.commands.ItemCommand;
import krakowiak.marcel.demo.domain.CheckList;
import krakowiak.marcel.demo.domain.Item;
import lombok.Synchronized;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CheckListToCheckListCommand implements Converter<CheckList,CheckListCommand> {



    @Override
    @Synchronized
    @Nullable
    public CheckListCommand convert(CheckList checkList){
        if(checkList==null){
            return null;
        }
        final CheckListCommand checkListCommand=new CheckListCommand();
        checkListCommand.setId(checkList.getId());
        checkListCommand.setName(checkList.getName());

        if(checkList.getItems()!=null && checkList.getItems().size()>0){
            for(Item item:checkList.getItems()){
                ItemCommand itemCommand=new ItemCommand();

                itemCommand.setChecked(item.isChecked());
                itemCommand.setName(item.getName());
                itemCommand.setId(item.getId());

                itemCommand.setOwner(checkListCommand);

                checkListCommand.getItems().add(itemCommand);
            }
        }

        return checkListCommand;

    }


}
