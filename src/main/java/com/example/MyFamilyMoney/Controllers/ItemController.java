package com.example.MyFamilyMoney.Controllers;

import com.example.MyFamilyMoney.models.Item;
import com.example.MyFamilyMoney.models.ItemGroup;
import com.example.MyFamilyMoney.models.TypeOperation;
import com.example.MyFamilyMoney.models.User;
import com.example.MyFamilyMoney.repo.ItemGroupRepository;
import com.example.MyFamilyMoney.repo.ItemRepository;
import com.example.MyFamilyMoney.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemGroupRepository itemGroupRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/item")
    public String itemMain(@CurrentSecurityContext(expression = "authentication?.name") String username, Model model) {
        Iterable<Item> items;
        User user = userRepository.findByUsername(username);
        User user1 = userRepository.findByUsername("root");
        if (user.equals(user1)) {
            items = itemRepository.findByUser(user);
        }
        else {
            items = itemRepository.findByUserOrUser(user, user1);
        }
        model.addAttribute("items", items);
        return "item-main";
    }

    @GetMapping("/item/add")
    public String itemAdd(@CurrentSecurityContext(expression = "authentication?.name") String username, Model model) {
        User user = userRepository.findByUsername(username);
        User rootUser = userRepository.findByUsername("root");
        Iterable<ItemGroup> itemGroups = itemGroupRepository.findByUserOrUser(user, rootUser);
        model.addAttribute("itemGroups", itemGroups);
        return "item-add";
    }

    @PostMapping("/item/add")
    public String itemsAdd(@CurrentSecurityContext(expression = "authentication?.name") String username, @RequestParam TypeOperation typeOperation, @RequestParam String name, @RequestParam ItemGroup itemGroup, Model model) {
        Item item = new Item(typeOperation, name, itemGroup);
        User user = userRepository.findByUsername(username);
        item.setUser(user);
        itemRepository.save(item);
        return "redirect:/item";
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
    public String itemPostUpdate(@PathVariable(value = "id") long id, @RequestParam String name, @RequestParam TypeOperation typeOperation, Model model) {
        Item item = itemRepository.findById(id).orElseThrow();
        item.setName(name);
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