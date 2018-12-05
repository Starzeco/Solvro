package krakowiak.marcel.demo.services;

import krakowiak.marcel.demo.commands.ItemCommand;
import krakowiak.marcel.demo.converts.ItemCommandToItem;
import krakowiak.marcel.demo.converts.ItemToItemCommand;
import krakowiak.marcel.demo.domain.CheckList;
import krakowiak.marcel.demo.domain.Item;
import krakowiak.marcel.demo.repositories.CheckListRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Null;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ItemServiceImpl implements ItemService {

    final private ItemCommandToItem itemCommandToItem;
    final private ItemToItemCommand itemToItemCommand;
    final private CheckListRepository checkListRepository;

    public ItemServiceImpl(ItemCommandToItem itemCommandToItem, ItemToItemCommand itemToItemCommand, CheckListRepository checkListRepository) {
        this.itemCommandToItem = itemCommandToItem;
        this.itemToItemCommand = itemToItemCommand;
        this.checkListRepository = checkListRepository;
    }




    @Override
    @Transactional
    public Set<ItemCommand> findSetOfItemsByCheckListName(String name) {
        Optional<CheckList>checkListOptional=checkListRepository.findByName(name);

        if(!checkListOptional.isPresent()){
            log.debug("Nie ma takiej checklisty ");
            //Error handling TODO
        }

        Set<ItemCommand> itemCommandSet=checkListOptional.get()
                .getItems()
                .stream()
                .map(item -> {
                    return itemToItemCommand.convert(item);

                }).collect(Collectors.toSet());

        return itemCommandSet;
    }

    @Override
    @Transactional
    public void updateItemCommand(ItemCommand command, String checkListName) {
        Optional<CheckList> detachedCheckList=checkListRepository.findByName(checkListName);
        if(!detachedCheckList.isPresent()){
            log.error("Nie znaleziono checklisty");
            //Error handling TODO
        }
        else{
            CheckList checkList=detachedCheckList.get();

            Optional<Item> itemOptional=checkList
                    .getItems()
                    .stream()
                    .filter(item->item.getId().equals(command.getId()))
                    .findFirst();

            if(!itemOptional.isPresent()){
                log.error("Nie znaleziono itemu");
            }
            else{
               Item itemFound=itemOptional.get();
               itemFound.setChecked(command.isChecked());
            }

        }
    }

    
    @Override
    @Transactional
    public ItemCommand saveCommand(ItemCommand command,String checkListName){
        Optional<CheckList> detachedCheckList=checkListRepository.findByName(checkListName);
        if(!detachedCheckList.isPresent()){
            log.debug("Nie znaleziono checklisty");
            //Error handling TODO
            return new ItemCommand();
        }else{
            CheckList checkList=detachedCheckList.get();

            Item item=itemCommandToItem.convert(command);
            item.setOwner(checkList);
            checkList.addItem(item);

            CheckList savedCheckList=checkListRepository.save(checkList);

            Optional<Item> savedItemOptional=savedCheckList
                    .getItems()
                    .stream()
                    .filter(itemo ->itemo.getName().equals(command.getName()))
                    .findFirst();

            return itemToItemCommand.convert(savedItemOptional.get());
        }


    }


    @Override
    @Transactional
    public void deleteItemCommandById(Long id, String checkListName) {
        Optional<CheckList> detachedCheckList=checkListRepository.findByName(checkListName);

        log.debug("Deleting item : "+id+": "+checkListName);

        if(detachedCheckList.isPresent()){
           CheckList checkList=detachedCheckList.get();

            log.debug("Found recipe");

            Optional<Item> itemOptional=checkList.getItems()
                    .stream()
                    .filter(item -> item.getId().equals(id))
                    .findFirst();
            if(itemOptional.isPresent()){
                log.debug("Found Item ");

                Item itemToDelete=itemOptional.get();
                itemToDelete.setOwner(null);
                checkList.getItems().remove(itemToDelete);
                checkListRepository.save(checkList);
            }



        }else{
            log.debug("Item id NOT FOUND "+id);
        }


    }


}
