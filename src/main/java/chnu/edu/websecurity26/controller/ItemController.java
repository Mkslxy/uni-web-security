package chnu.edu.websecurity26.controller;

/*
  @author   oleksii
  @project  web-security26
  @class    ItemController
  @version  1.0.0 
  @since    15.09.2026
*/

import chnu.edu.websecurity26.model.Item;
import chnu.edu.websecurity26.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    public List<Item> getAll() {
        return itemService.getAllItems();
    }

    @GetMapping("/{id}")
    public Item getItem(@PathVariable String id) {
        return itemService.getItem(id);
    }

    @PostMapping
    public Item createItem(@RequestBody Item item) {
        return itemService.createItem(item);
    }

    @PutMapping
    public Item updateItem(@RequestBody Item item) {
        return itemService.updateItem(item);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable String id) {
        itemService.deleteItem(id);
    }
}
