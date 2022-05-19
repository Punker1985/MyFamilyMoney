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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class ItemGroupController {

    @Autowired
    private ItemGroupRepository itemGroupRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/itemGroup")
    public String itemGroupMain(@CurrentSecurityContext(expression = "authentication?.name") String username, Model model) {
        User user = userRepository.findByUsername(username);
        User rootUser = userRepository.findByUsername("root");
        Iterable<ItemGroup> itemGroups = itemGroupRepository.findByUserOrUserAndTypeOperation(user,rootUser,TypeOperation.SPENDING);
        model.addAttribute("itemGroups", itemGroups);
        return "itemGroup-spending";
    }
    @GetMapping("/itemGroup/spending")
    public String itemGroupSpending(@CurrentSecurityContext(expression = "authentication?.name") String username, Model model) {
        User user = userRepository.findByUsername(username);
        User rootUser = userRepository.findByUsername("root");
        Iterable<ItemGroup> itemGroups = itemGroupRepository.findByUserOrUserAndTypeOperation(user,rootUser,TypeOperation.SPENDING);
        model.addAttribute("itemGroups", itemGroups);
        return "itemGroup-spending";
    }

    @GetMapping("/itemGroup/receipt")
    public String itemGroupReceipt(@CurrentSecurityContext(expression = "authentication?.name") String username, Model model) {
        User user = userRepository.findByUsername(username);
        User rootUser = userRepository.findByUsername("root");
        Iterable<ItemGroup> itemGroups = itemGroupRepository.findByUserOrUserAndTypeOperation(user,rootUser,TypeOperation.RECEIPT);
        model.addAttribute("itemGroups", itemGroups);
        return "itemGroup-receipt";
    }


    @GetMapping("/itemGroup/add")
    public String itemGroupAdd(Model model) {
        return "itemGroup-add";
    }

    @PostMapping("/itemGroup/add")
    public String itemsGroupAdd(@CurrentSecurityContext(expression = "authentication?.name") String username, @RequestParam TypeOperation typeOperation, @RequestParam String name, Model model) {
        User user = userRepository.findByUsername(username);
        ItemGroup itemGroup = new ItemGroup(typeOperation, name, user);
        itemGroupRepository.save(itemGroup);
        return "redirect:/itemGroup";
    }

    @GetMapping("/itemGroup/{id}/edit")
    public String itemGroupEdit(@PathVariable(value = "id") long id, Model model) {
        if (!itemGroupRepository.existsById(id)) {
            return "redirect:/itemGroup";
        }
        Optional<ItemGroup> itemGroup = itemGroupRepository.findById(id);
        ArrayList<ItemGroup> res = new ArrayList<>();
        itemGroup.ifPresent(res::add);
        model.addAttribute("itemGroup", res);
        return "itemGroup-edit";
    }

    @PostMapping("/itemGroup/{id}/edit")
    public String itemGroupPostUpdate(@PathVariable(value = "id") long id, @RequestParam String name, @RequestParam TypeOperation typeOperation, Model model) {
        ItemGroup itemGroup = itemGroupRepository.findById(id).orElseThrow();
        itemGroup.setName(name);
        itemGroup.setTypeOperation(typeOperation);
        itemGroupRepository.save(itemGroup);
        return "redirect:/itemGroup";
    }

    @PostMapping("/itemGroup/{id}/remove")
    public String itemGroupPostDelete(@PathVariable(value = "id") long id, Model model) {
        ItemGroup itemGroup = itemGroupRepository.findById(id).orElseThrow();
        itemGroupRepository.delete(itemGroup);
        return "redirect:/itemGroup";
    }
}