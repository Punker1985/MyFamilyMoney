package com.example.MyFamilyMoney.Controllers;

import com.example.MyFamilyMoney.models.*;
import com.example.MyFamilyMoney.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class TransferController {
    @Autowired
    private TransfersRepository transfersRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/transfers")
    public String transfersMain(@CurrentSecurityContext(expression = "authentication?.name") String username, Model model) {
        User user = userRepository.findByUsername(username);
        Iterable<Transfer> transfers = transfersRepository.findAll();
        model.addAttribute("transfers", transfers);
        return "transfer-main";
    }

    @GetMapping("/transfers/add")
    public String transfersAdd(@CurrentSecurityContext(expression = "authentication?.name") String username, Model model) {
        User user = userRepository.findByUsername(username);
        Iterable<Account> accounts = accountRepository.findAll();
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String dateNowString = date.format(formatterDate);
        model.addAttribute("accounts", accounts);
        model.addAttribute("dateNow", dateNowString);
        return "transfer-add";
    }

    @PostMapping("/transfers/add")
    public String transfersAdd(@RequestParam String description, @RequestParam String amount, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime date, @RequestParam Account accountSpending, @RequestParam Account accountReceipt, Model model) {
        double amountDouble = Double.parseDouble(amount) * 100;
        long amountLong = (long) amountDouble;
        Transfer transfer = new Transfer(accountSpending, accountReceipt, amountLong, date, description);
        transfersRepository.save(transfer);
        accountSpending.setEndBalance(accountSpending.getEndBalance() - amountLong);
        accountReceipt.setEndBalance(accountReceipt.getEndBalance() + amountLong);
        accountRepository.save(accountReceipt);
        accountRepository.save(accountSpending);
        return "redirect:/transfers";
    }

    @GetMapping("/transfers/{id}/edit")
    public String transfersEdit(@PathVariable(value = "id") long id, @CurrentSecurityContext(expression = "authentication?.name") String username, Model model) {
        if (!transfersRepository.existsById(id)) {
            return "redirect:/transfers";
        }
        Optional<Transfer> transfers = transfersRepository.findById(id);
        ArrayList<Transfer> res = new ArrayList<>();
        transfers.ifPresent(res::add);
        model.addAttribute("transfers", res);
        //User user = userRepository.findByUsername(username);
        Iterable<Account> accounts = accountRepository.findAll();
        model.addAttribute("accounts", accounts);
        return "transfer-edit";
    }

    @PostMapping("/transfers/{id}/edit")
    public String transfersUpdate(@PathVariable(value = "id") long id, @RequestParam String description, @RequestParam String amount, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime date, @RequestParam Account accountSpending, @RequestParam Account accountReceipt, Model model) {
        Transfer transfer = transfersRepository.findById(id).orElseThrow();
        double amountDouble = Double.parseDouble(amount) * 100;
        long amountLong = (long) amountDouble;
        accountSpending.setEndBalance(accountSpending.getEndBalance() - (amountLong - transfer.getAmount()));
        accountReceipt.setEndBalance(accountReceipt.getEndBalance() + (amountLong - transfer.getAmount()));
        accountRepository.save(accountReceipt);
        accountRepository.save(accountSpending);
        transfer.setDescription(description);
        transfer.setAmount(amountLong);
        transfer.setDate(date);
        transfer.setAccountReceipt(accountReceipt);
        transfer.setAccountSpending(accountSpending);
        transfersRepository.save(transfer);
        return "redirect:/transfers";
    }

    @PostMapping("/transfers/{id}/remove")
    public String transfersDelete(@PathVariable(value = "id") long id, Model model) {
        Transfer transfer = transfersRepository.findById(id).orElseThrow();
        Account accountSpending = transfer.getAccountSpending();
        Account accountReceipt = transfer.getAccountReceipt();
        accountSpending.setEndBalance(accountSpending.getEndBalance() + transfer.getAmount());
        accountReceipt.setEndBalance(accountReceipt.getEndBalance() - transfer.getAmount());
        transfersRepository.delete(transfer);
        accountRepository.save(accountReceipt);
        accountRepository.save(accountSpending);
        return "redirect:/transfers";

    }
}
