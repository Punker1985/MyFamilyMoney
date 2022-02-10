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
import java.time.format.DateTimeFormatter;
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

    @GetMapping("/operations{idAccount}/add/spending")
    public String operationsAdd(@PathVariable(value = "idAccount") Long idAccount, @CurrentSecurityContext(expression = "authentication?.name") String username, Model model) {
        User user = userRepository.findByUsername(username);
        Account account = accountRepository.findById(idAccount).orElseThrow();
        Iterable<Account> accounts = accountRepository.findAllByUser(user);
        Iterable<Item> items = itemRepository.findAllByTypeOperation(TypeOperation.SPENDING);
        Iterable<Counteragent> counteragents = counteragentRepository.findAll();
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String dateNowString = date.format(formatterDate);
        model.addAttribute("activAccount",account);
        model.addAttribute("accounts", accounts);
        model.addAttribute("items", items);
        model.addAttribute("counteragents", counteragents);
        model.addAttribute("dateNow", dateNowString);
        return "operations-add-spending";
    }

    @PostMapping("/operations/add/spending")
    public String operationsAdd(@RequestParam String description, @RequestParam String amount, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime date, @RequestParam Account account, @RequestParam Item item, @RequestParam Counteragent counteragent, Model model) {
        double amountDouble = Double.parseDouble(amount) * 100;
        long amountLong = (long) amountDouble;
        Operations operation = new Operations(description, account, counteragent, date, item, amountLong);
        if (operation.getDescription().equals("")) {
            operation.setDescription(operation.getItem().getName());
        }
        operation.setTypeOperation(TypeOperation.SPENDING);
        operationsRepository.save(operation);
        account.setEndBalance(account.getEndBalance() - amountLong);
        accountRepository.save(account);
        return "redirect:/transactions/" + account.getId();
    }

    @GetMapping("/operations{idAccount}/add/receipt")
    public String operationsAddReceipt(@PathVariable(value = "idAccount") Long idAccount, @CurrentSecurityContext(expression = "authentication?.name") String username, Model model) {
        User user = userRepository.findByUsername(username);
        Account account = accountRepository.findById(idAccount).orElseThrow();
        Iterable<Account> accounts = accountRepository.findAllByUser(user);
        Iterable<Item> items = itemRepository.findAllByTypeOperation(TypeOperation.RECEIPT);
        Iterable<Counteragent> counteragents = counteragentRepository.findAll();
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String dateNowString = date.format(formatterDate);
        model.addAttribute("activAccount",account);
        model.addAttribute("accounts", accounts);
        model.addAttribute("items", items);
        model.addAttribute("counteragents", counteragents);
        model.addAttribute("dateNow", dateNowString);
        return "operations-add-receipt";
    }

    @PostMapping("/operations/add/receipt")
    public String operationsAddReceipt(@RequestParam String description, @RequestParam String amount, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime date, @RequestParam Account account, @RequestParam Item item, @RequestParam Counteragent counteragent, Model model) {
        double amountDouble = Double.parseDouble(amount) * 100;
        long amountLong = (long) amountDouble;
        Operations operation = new Operations(description, account, counteragent, date, item, amountLong);
        operation.setTypeOperation(TypeOperation.RECEIPT);
        if (operation.getDescription().equals("")) {
            operation.setDescription(operation.getItem().getName());
        }
        operationsRepository.save(operation);
        account.setEndBalance(account.getEndBalance() + amountLong);
        accountRepository.save(account);
        return "redirect:/transactions/" + account.getId();
    }

    @GetMapping("/operations/{id}/edit")
    public String operationsEdit(@PathVariable(value = "id") long id, @CurrentSecurityContext(expression = "authentication?.name") String username, Model model) {
        if (!operationsRepository.existsById(id)) {
            return "redirect:/";
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
        if (operations.getDescription().equals("")) {
            operations.setDescription(operations.getItem().getName());
        }
        operationsRepository.save(operations);
        return "redirect:/transactions/" + account.getId();
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
        return "redirect:/transactions/" + account.getId();

    }
}
