package krakowiak.marcel.demo.controllers;

import krakowiak.marcel.demo.commands.CheckListCommand;
import krakowiak.marcel.demo.domain.CheckList;
import krakowiak.marcel.demo.services.CheckListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;


@RestController
public class CheckListController {

    CheckListService checkListService;

    @Autowired
    public CheckListController(CheckListService checkListService) {
        this.checkListService = checkListService;
    }

    @PostMapping("/lists")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewCheckList(@RequestBody String name){
        CheckListCommand command=new CheckListCommand(name);
        checkListService.saveCheckListCommand(command);
    }

    @DeleteMapping("/lists/{name}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCheckList(@PathVariable String name){
        checkListService.deleteByName(name);
    }

    @GetMapping(value="/lists",produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Set<String> getListOfCheckLists(){
        Set<String> checkListStrings=checkListService.getCheckList()
                .stream()
                .map(CheckList::getName).collect(Collectors.toSet());

        return checkListStrings;
    }


}
