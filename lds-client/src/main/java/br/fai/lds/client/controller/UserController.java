package br.fai.lds.client.controller;

import br.fai.lds.client.service.UserService;
import br.fai.lds.models.entities.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

        //Para testes
//        userService = null;

        List<UserModel> users = userService.find();

        if (users == null || users.isEmpty()) {
            model.addAttribute("users", new ArrayList<UserModel>());
        } else {

            model.addAttribute("users", users);
        }

        return "user/list";

    }

    @GetMapping("/detail/{id}")
    public String getDetailPage(@PathVariable("id") final int id, final Model model) {

        UserModel user = (UserModel) userService.findById(id);

        if (user == null) {
            return "redirect:/user/list";
        }

        model.addAttribute("user", user);


        return "user/detail";
    }

    @GetMapping("/edit/{id}")
    public String getEditPage(@PathVariable("id") final int id, final Model model) {

        UserModel user = (UserModel) userService.findById(id);

        if (user == null) {
            return "redirect:/user/list";
        }

        model.addAttribute("user", user);


        return "user/edit";
    }

    @PostMapping("/update")
    public String update(final UserModel user, final Model model) {

        userService.update(user.getId(), user);


        return getDetailPage(user.getId(), model);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") final int id, final Model model) {

        userService.deleteById(id);

        return getUsers(model);
    }

    @GetMapping("/report/read-all")
    public ResponseEntity<byte[]> generateReport() {


        return null;
    }

}