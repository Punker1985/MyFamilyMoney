package com.example.MyFamilyMoney.Controllers;

import com.example.MyFamilyMoney.models.Account;
import com.example.MyFamilyMoney.models.AccountType;
import com.example.MyFamilyMoney.models.Counteragent;
import com.example.MyFamilyMoney.repo.AccountRepository;
import com.example.MyFamilyMoney.repo.CounteragentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class accountController {
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/account")
    public String accountMain(Model model) {
        Iterable<Account> accounts = accountRepository.findAll();
        model.addAttribute("accounts", accounts);
        return "account-main";
    }

    @GetMapping("/account/add")
    public String accountAdd(Model model) {
        return "account-add";
    }

    @PostMapping("/account/add")
    public String accountAdd(@RequestParam String name, @RequestParam AccountType type, Model model) {
        Account account = new Account(name, type);
        accountRepository.save(account);
        return "redirect:/account";
    }

    @GetMapping("/account/{id}/edit")
    public String accountEdit(@PathVariable(value = "id") long id, Model model) {
        if (!accountRepository.existsById(id)) {
            return "redirect:/account";
        }
        Optional<Account> account = accountRepository.findById(id);
        ArrayList<Account> res = new ArrayList<>();
        account.ifPresent(res::add);
        model.addAttribute("account", res);
        return "account-edit";
    }

    @PostMapping("/account/{id}/edit")
    public String accountUpdate(@PathVariable(value = "id") long id, @RequestParam String name, @RequestParam AccountType type, Model model) {
        Account account = accountRepository.findById(id).orElseThrow();
        account.setName(name);
        account.setType(type);
        accountRepository.save(account);
        return "redirect:/account";
    }

    @PostMapping("/account/{id}/remove")
    public String accountDelete(@PathVariable(value = "id") long id, Model model) {
        Account account = accountRepository.findById(id).orElseThrow();
        accountRepository.delete(account);
        return "redirect:/account";

    }
}