package br.fai.lds.client.controller;

import br.fai.lds.models.entities.UserModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {

    @GetMapping("/sign-up")
    public String getSignUpPage() {
        return "account/sign-up";
    }

    @GetMapping("/sign-in")
    public String getSingInPage() {
        return "account/sign-in";
    }

    @GetMapping("/password-recovery")
    public String getPasswordRecoveryPage() {
        return "account/password-recovery";
    }

    @PostMapping("/create")
    public String create(UserModel user){

        return "redirect:/account/sign-in";
    }

    @PostMapping("/login")
    public String login(UserModel model){
        return "redirect:/account/sign-up";
    }

}
