package com.example.MyFamilyMoney.Controllers;

import com.example.MyFamilyMoney.models.*;
import com.example.MyFamilyMoney.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OperationsRepository operationsRepository;
    @Autowired
    private TransfersRepository transfersRepository;

    @GetMapping("/account")
    public String accountMain(@CurrentSecurityContext(expression = "authentication?.name") String username, Model model) {
        User user = userRepository.findByUsername(username);
        Iterable<Account> accounts = accountRepository.findAllByUser(user);
        model.addAttribute("accounts", accounts);
        return "account-main";
    }

    @GetMapping("/account/add")
    public String accountAdd(Model model, Account account) {
        return "account-add";
    }

    @PostMapping("/account/add")
    public String accountAdd(@Valid Account account, BindingResult bindingResult, @CurrentSecurityContext(expression = "authentication?.name") String username) {
        if (bindingResult.hasErrors()) {
            return "account-add";
        }
        User user = userRepository.findByUsername(username);
        account.setStartBalance(account.getStartBalance() * 100);
        account.setEndBalance(account.getStartBalance());
        account.setUser(user);
        accountRepository.save(account);
        return "redirect:/";
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
        return "redirect:/";
    }

    @PostMapping("/account/{id}/remove")
    public String accountDelete(@PathVariable(value = "id") long id, Model model) {
        String answer;
        Account account = accountRepository.findById(id).orElseThrow();
        Iterable<Operations> operations = operationsRepository.findAllByAccount(account);
        Iterable<Transfer> transfersReceipt = transfersRepository.findAllByAccountReceipt(account);
        Iterable<Transfer> transfersSpending = transfersRepository.findAllByAccountSpending(account);
        if (operations.iterator().hasNext() | transfersReceipt.iterator().hasNext() | transfersSpending.iterator().hasNext()) {
            answer = "redirect:/errorDeleteAccount";
        }
        else {
            accountRepository.delete(account);
            answer = "redirect:/";
        }
        return answer;

    }
}