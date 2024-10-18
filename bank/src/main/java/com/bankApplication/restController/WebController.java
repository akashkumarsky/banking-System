package com.bankApplication.restController;

import com.bankApplication.entity.Account;
import com.bankApplication.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WebController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("account", new Account());
        return "create";
    }

    @PostMapping("/create")
    public String createAccount(@ModelAttribute Account account) {
        accountService.createAccount(account);
        return "redirect:/";
    }

    @GetMapping("/view")
    public String viewForm(@RequestParam(required = false) Long id, Model model) {
        if (id != null) {
            model.addAttribute("account", accountService.getAccount(id).orElse(null));
        }
        return "view";
    }

    @PostMapping("/deposit")
    public String deposit(@RequestParam(required = false)  Long id, @RequestParam double amount, Model model) {
        try {
            Account updatedAccount = accountService.deposit(id, amount);
            model.addAttribute("account", updatedAccount);
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "view"; // This should be the view template that shows the updated account information
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestParam Long id, @RequestParam double amount, Model model) {
        try {
            Account updatedAccount = accountService.withdraw(id, amount);
            model.addAttribute("account", updatedAccount);
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "view"; // This should be the view template that shows the updated account information
    }
}
