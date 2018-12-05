package krakowiak.marcel.demo.services;

import krakowiak.marcel.demo.commands.CheckListCommand;
import krakowiak.marcel.demo.domain.CheckList;
import krakowiak.marcel.demo.domain.Item;

import java.util.Set;

public interface CheckListService {
    Set<CheckList> getCheckList();

    CheckList findByName(String name);

    CheckListCommand saveCheckListCommand(CheckListCommand command);

    CheckListCommand findCommandByName(String name);


    void deleteByName(String name);

}
