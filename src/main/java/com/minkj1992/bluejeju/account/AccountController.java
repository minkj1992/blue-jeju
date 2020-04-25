package com.minkj1992.bluejeju.account;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
<<<<<<< HEAD
=======
@RequestMapping(path="/account")
>>>>>>> lecture
public class AccountController {

    @GetMapping(value="/sign-up")
    public String signUpForm(Model model) {
<<<<<<< HEAD
        model.addAttribute(new SignUpForm());
=======
//        model.addAttribute("AccountForm", new AccountForm());
>>>>>>> lecture
        return "account/sign-up";
    }
}
