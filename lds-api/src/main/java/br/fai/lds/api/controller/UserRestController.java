package br.fai.lds.api.controller;


import br.fai.lds.api.service.UserRestService;
import br.fai.lds.models.entities.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserRestController {

    @Autowired
    private UserRestService userService;

    @GetMapping("/find")
    public ResponseEntity<List<UserModel>> find() {

        List<UserModel> users = userService.find();

        if (users == null) {
            return ResponseEntity.badRequest().build();
        }


        return ResponseEntity.ok(users);
    }
}
