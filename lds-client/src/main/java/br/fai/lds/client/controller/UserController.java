package br.fai.lds.client.controller;

import br.fai.lds.client.service.UserService;
import br.fai.lds.models.entities.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public String getUsers(final Model model) {

        List<UserModel> users = userService.find();

        if (users == null || users.isEmpty()) {
            model.addAttribute("users", new ArrayList<UserModel>());
        } else {

            model.addAttribute("users", users);
        }

        return "user/list";

    }

    @GetMapping("/detail/{id}")
    public String getDetailPage(@PathVariable("id") final int id, final Model model){

        UserModel user = (UserModel) userService.findById(id);

        if (user == null){
            return "redirect:/user/list";
        }

        model.addAttribute("user", user);


        return "user/detail";
    }

    @GetMapping("/edit/{id}")
    public String getEditPage(@PathVariable("id") final int id, final Model model){

        UserModel user = (UserModel) userService.findById(id);

        if (user == null){
            return "redirect:/user/list";
        }

        model.addAttribute("user", user);


        return "user/edit";
    }
}