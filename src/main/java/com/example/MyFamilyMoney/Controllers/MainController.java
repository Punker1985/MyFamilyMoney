package com.example.MyFamilyMoney.Controllers;

import com.example.MyFamilyMoney.models.*;
import com.example.MyFamilyMoney.repo.AccountRepository;
import com.example.MyFamilyMoney.repo.OperationsRepository;
import com.example.MyFamilyMoney.repo.TransfersRepository;
import com.example.MyFamilyMoney.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.thymeleaf.expression.Lists;

import javax.swing.text.html.HTMLDocument;
import java.util.*;

@Controller
public class MainController {
    @Autowired
    private OperationsRepository operationsRepository;
    @Autowired
    private TransfersRepository transfersRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String home(@CurrentSecurityContext(expression = "authentication?.name") String username, Model model) {
        User user = userRepository.findByUsername(username);
        Iterable<Account> accounts = accountRepository.findAllByUser(user);
        model.addAttribute("accounts", accounts);
        return "home";
    }

    @GetMapping("/transactions/{id}")
    public String transaction(@PathVariable(value = "id") long id, Model model) {
        Account account = accountRepository.findById(id).orElseThrow();
        Iterable<Operations> operations = operationsRepository.findAllByAccount(account);
        Iterable<Transfer> transfersReceipt = transfersRepository.findAllByAccountReceipt(account);
        transfersReceipt.forEach(transfer -> transfer.setTypeOperation(TypeOperation.RECEIPT));
        Iterable<Transfer> transfersSpending = transfersRepository.findAllByAccountSpending(account);
        transfersSpending.forEach(transfer -> transfer.setTypeOperation(TypeOperation.SPENDING));
        List transactions = new ArrayList<Transactions>();
        operations.forEach(transactions::add);
        transfersReceipt.forEach(transactions::add);
        transfersSpending.forEach(transactions::add);
        transactions.sort(Comparator.comparing(Transactions::getDate).reversed());
        model.addAttribute("transactions", transactions);
        model.addAttribute("title", "Транзакции по счету");
        model.addAttribute("accountName", account.getName());
        model.addAttribute("idAccount", account.getId());
        return "transaction";
    }

    @GetMapping("/transaction/{id}")
    public String home(@PathVariable(value = "id") long id, Model model) {
        if (operationsRepository.existsById(id)) {
            return "redirect:/operations/{id}/edit";
        }
        if (transfersRepository.existsById(id)) {
            return "redirect:/transfers/{id}/edit";
        }
        return "redirect:/";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "О нас");
        return "about";
    }

}
