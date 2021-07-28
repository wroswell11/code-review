package com.carsaver.codereview.web;

import com.carsaver.codereview.model.User;
import com.carsaver.codereview.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    private List<User> findAll(){
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    private ResponseEntity<User> findById(@PathVariable Long id){
        return ResponseEntity.of(userService.findById(id));
    }

}
