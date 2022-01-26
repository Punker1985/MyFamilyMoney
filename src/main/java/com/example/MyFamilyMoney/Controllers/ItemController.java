package com.example.MyFamilyMoney.Controllers;

import com.example.MyFamilyMoney.models.Item;
import com.example.MyFamilyMoney.models.TypeOperation;
import com.example.MyFamilyMoney.repo.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/item")
    public String blogMain(Model model) {
        Iterable<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "item-main";
    }

    @GetMapping("/item/add")
    public String itemAdd(Model model) {
        return "item-add";
    }

       @PostMapping("/item/add")
    public String itemsAdd(@RequestParam TypeOperation typeOperation, @RequestParam String name, @RequestParam String description, Model model) {
        Item item = new Item(typeOperation, name, description);
        itemRepository.save(item);
        return "redirect:/item";
    }
    @GetMapping("/item/{id}")
    public String itemDetails(@PathVariable(value = "id") long id, Model model) {
        if (!itemRepository.existsById(id)) {
            return "redirect:/item";
        }
        Optional<Item> item = itemRepository.findById(id);
        ArrayList<Item> res = new ArrayList<>();
        item.ifPresent(res::add);
        model.addAttribute("item", res);
        return "item-detail";
    }
    @GetMapping("/item/{id}/edit")
    public String itemEdit(@PathVariable(value = "id") long id, Model model) {
        if (!itemRepository.existsById(id)) {
            return "redirect:/item";
        }
        Optional<Item> item = itemRepository.findById(id);
        ArrayList<Item> res = new ArrayList<>();
        item.ifPresent(res::add);
        model.addAttribute("item", res);
        return "item-edit";
    }
    @PostMapping("/item/{id}/edit")
    public String itemPostUpdate(@PathVariable(value = "id") long id, @RequestParam String name, @RequestParam String description, @RequestParam TypeOperation typeOperation, Model model) {
        Item item = itemRepository.findById(id).orElseThrow();
        item.setName(name);
        item.setDescription(description);
        item.setTypeOperation(typeOperation);
        itemRepository.save(item);
        return "redirect:/item";
    }
    @PostMapping("/item/{id}/remove")
    public String itemPostDelete(@PathVariable(value = "id") long id, Model model) {
        Item item = itemRepository.findById(id).orElseThrow();
        itemRepository.delete(item);
        return "redirect:/item";
    }
}