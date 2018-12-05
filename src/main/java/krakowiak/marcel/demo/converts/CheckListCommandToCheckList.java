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
public class CheckListCommandToCheckList implements Converter<CheckListCommand,CheckList> {


    @Override
    @Nullable
    @Synchronized
    public CheckList convert(CheckListCommand checkListCommand) {
        if(checkListCommand==null){
            return null;
        }

        final CheckList checkList=new CheckList();

        checkList.setId(checkListCommand.getId());
        checkList.setName(checkListCommand.getName());

        if(checkListCommand.getItems()!=null && checkListCommand.getItems().size()>0){
            for(ItemCommand itemCommand:checkListCommand.getItems()){
                Item item=new Item();
                item.setChecked(itemCommand.isChecked());
                item.setId(itemCommand.getId());
                item.setName(itemCommand.getName());
                item.setOwner(checkList);

                checkList.getItems().add(item);
            }
        }
        return checkList;
    }
}
