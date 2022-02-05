package com.example.MyFamilyMoney.Controllers;

import com.example.MyFamilyMoney.models.*;
import com.example.MyFamilyMoney.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class OperationsContoller {

    @Autowired
    private OperationsRepository operationsRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private CounteragentRepository counteragentRepository;

    @GetMapping("/operations")
    public String operationsMain(@CurrentSecurityContext(expression = "authentication?.name") String username, Model model) {
        User user = userRepository.findByUsername(username);
        Iterable<Operations> operations = operationsRepository.findAll();
        model.addAttribute("operations", operations);
        return "operations-main";
    }

    @GetMapping("/operations/add")
    public String operationsAdd(@CurrentSecurityContext(expression = "authentication?.name") String username, Model model) {
        User user = userRepository.findByUsername(username);
        Iterable<Account> accounts = accountRepository.findAllByUser(user);
        Iterable<Item> items = itemRepository.findAll();
        Iterable<Counteragent> counteragents = counteragentRepository.findAll();
        model.addAttribute("accounts", accounts);
        model.addAttribute("items", items);
        model.addAttribute("counteragents", counteragents);
        return "operations-add";
    }

    @PostMapping("/operations/add")
    public String operationsAdd(@RequestParam String description, @RequestParam String amount, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime date, @RequestParam Account account, @RequestParam Item item, @RequestParam Counteragent counteragent, Model model) {
        double amountDouble = Double.parseDouble(amount) * 100;
        long amountLong = (long) amountDouble;
        Operations operation = new Operations(description, account, counteragent, date, item, amountLong);
        operationsRepository.save(operation);
        if (item.getTypeOperation().equals(TypeOperation.SPENDING)) {
            account.setEndBalance(account.getEndBalance() - amountLong);
        }
        else {
            account.setEndBalance(account.getEndBalance() + amountLong);
        }
        accountRepository.save(account);
        return "redirect:/operations";
    }

    @GetMapping("/operations/{id}/edit")
    public String operationsEdit(@PathVariable(value = "id") long id, @CurrentSecurityContext(expression = "authentication?.name") String username, Model model) {
        if (!operationsRepository.existsById(id)) {
            return "redirect:/operations";
        }
        Optional<Operations> operations = operationsRepository.findById(id);
        ArrayList<Operations> res = new ArrayList<>();
        operations.ifPresent(res::add);
        model.addAttribute("operations", res);
        User user = userRepository.findByUsername(username);
        Iterable<Account> accounts = accountRepository.findAllByUser(user);
        Iterable<Item> items = itemRepository.findAll();
        Iterable<Counteragent> counteragents = counteragentRepository.findAll();
        model.addAttribute("accounts", accounts);
        model.addAttribute("items", items);
        model.addAttribute("counteragents", counteragents);
        return "operations-edit";
    }

    @PostMapping("/operations/{id}/edit")
    public String operationsUpdate(@PathVariable(value = "id") long id, @RequestParam String description, @RequestParam String amount, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime date, @RequestParam Account account, @RequestParam Item item, @RequestParam Counteragent counteragent, Model model) {
        Operations operations = operationsRepository.findById(id).orElseThrow();
        double amountDouble = Double.parseDouble(amount) * 100;
        long amountLong = (long) amountDouble;
        if (item.getTypeOperation().equals(TypeOperation.SPENDING)) {
            account.setEndBalance(account.getEndBalance() + (operations.getAmount() - amountLong));
        }
        else {
            account.setEndBalance(account.getEndBalance() - (operations.getAmount() - amountLong));
        }
        accountRepository.save(account);
        operations.setDescription(description);
        operations.setAmount(amountLong);
        operations.setDate(date);
        operations.setItem(item);
        operations.setAccount(account);
        operations.setCounteragent(counteragent);
        operationsRepository.save(operations);
        return "redirect:/operations";
    }

    @PostMapping("/operations/{id}/remove")
    public String operationsDelete(@PathVariable(value = "id") long id, Model model) {
        Operations operations = operationsRepository.findById(id).orElseThrow();
        Account account = operations.getAccount();
        if (operations.getItem().getTypeOperation().equals(TypeOperation.SPENDING)) {
            account.setEndBalance(account.getEndBalance() + operations.getAmount());
        }
        else {
            account.setEndBalance(account.getEndBalance() - operations.getAmount());
        }
        operationsRepository.delete(operations);
        accountRepository.save(account);
        return "redirect:/operations";

    }
}
