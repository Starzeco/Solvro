package krakowiak.marcel.demo.controllers;


import krakowiak.marcel.demo.commands.ItemCommand;
import krakowiak.marcel.demo.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class ItemController {


    final private ItemService service;


    @Autowired
    public ItemController(ItemService service) {
        this.service = service;

    }


    @GetMapping(value = "/lists/{name}/items",produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Set<String> getItems(@PathVariable String name){
        return service.findSetOfItemsByCheckListName(name).stream().map(item -> item.getName()).collect(Collectors.toSet());
    }

    @PostMapping("/lists/{name}/items")
    @ResponseStatus(HttpStatus.CREATED)
    public Long postItem(@PathVariable String name,@RequestBody String nameOfNewItem){

        return service.saveCommand(new ItemCommand(nameOfNewItem),name).getId();
    }

    @PatchMapping("/lists/{name}/items/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void patchItem(@PathVariable String name,@PathVariable Long id, @RequestBody Boolean checked){
        service.updateItemCommand(new ItemCommand(id,checked),name);
    }

    @DeleteMapping("/lists/{name}/items/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void removeItem(@PathVariable String name,@PathVariable Long id){
        service.deleteItemCommandById(id,name);
    }





}
