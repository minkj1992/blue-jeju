package com.minkj1992.bluejeju.account;

import com.minkj1992.bluejeju.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/account")
@RequiredArgsConstructor
public class AccountController {

    @GetMapping(value="/sign-up")
    public String createForm(Model model) {
        model.addAttribute("AccountForm", new AccountForm());
        return "account/sign-up";
    }
}
