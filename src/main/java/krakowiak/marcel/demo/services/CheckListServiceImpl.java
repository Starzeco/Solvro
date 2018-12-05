package krakowiak.marcel.demo.services;

import krakowiak.marcel.demo.commands.CheckListCommand;
import krakowiak.marcel.demo.converts.CheckListCommandToCheckList;
import krakowiak.marcel.demo.converts.CheckListToCheckListCommand;
import krakowiak.marcel.demo.domain.CheckList;
import krakowiak.marcel.demo.repositories.CheckListRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class CheckListServiceImpl implements CheckListService {



    private final CheckListRepository checkListRepository;
    private final CheckListCommandToCheckList checkListCommandToCheckList;
    private final CheckListToCheckListCommand checkListToCheckListCommand;

    public CheckListServiceImpl(CheckListRepository checkListRepository, CheckListCommandToCheckList checkListCommandToCheckList, CheckListToCheckListCommand checkListToCheckListCommand) {
        this.checkListRepository = checkListRepository;
        this.checkListCommandToCheckList = checkListCommandToCheckList;
        this.checkListToCheckListCommand = checkListToCheckListCommand;
    }

    @Override
    public Set<CheckList> getCheckList() {
        Set<CheckList> checkListSet=new HashSet<>();
        log.debug("I AM IN CHECKLISTSERVICE");
        checkListRepository.findAll().iterator().forEachRemaining(checkListSet::add);

        return checkListSet;
    }

    @Override
    public CheckList findByName(String name) {
        Optional<CheckList> checkListOptional=checkListRepository.findByName(name);
        if(!checkListOptional.isPresent()){
            log.debug("NOT FOUND CHECKLIST");
            // Exception notFound TODO
        }
        return checkListOptional.get();
    }



    @Override
    @Transactional
    public CheckListCommand saveCheckListCommand(CheckListCommand command) {
        CheckList detachedCheckList=checkListCommandToCheckList.convert(command);
        if(checkListRepository.existsByName(detachedCheckList.getName())){
            log.debug("JUZ JEST LISTA O TAKIEJ NAZWIE");
            //TODO EXCEPTION
        }

        CheckList savedCheckList=checkListRepository.save(detachedCheckList);
        log.debug("Saved Recipe id : "+savedCheckList.getId());

        return checkListToCheckListCommand.convert(savedCheckList);
    }

    @Override
    @Transactional
    public CheckListCommand findCommandByName(String name) {
        return checkListToCheckListCommand.convert(findByName(name));
    }


    @Override
    public void deleteByName(String name){
        Optional<CheckList> checkListOptional=checkListRepository.findByName(name);
        if(!checkListOptional.isPresent()){
            log.debug("NOT FOUND CHECKLIST delete");
            //EXCEPTION notFound TODO
        }

        checkListRepository.deleteByName(name);
    }
}
