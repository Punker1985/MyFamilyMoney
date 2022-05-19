package com.example.MyFamilyMoney.Controllers;

import com.example.MyFamilyMoney.models.Account;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/errorDeleteAccount")
    public String errorDeleteAccount(Model model) {
        return "error-delete-account";
    }
}
