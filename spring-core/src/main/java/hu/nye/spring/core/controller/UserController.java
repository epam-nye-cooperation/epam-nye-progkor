package hu.nye.spring.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import hu.nye.spring.core.entity.UserEntity;
import hu.nye.spring.core.request.UserRequest;
import hu.nye.spring.core.service.IUserService;
import jakarta.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/users")
    public UserEntity saveUser(@RequestBody @Valid UserRequest request) {
        return userService.saveUser(request);
    }

    @GetMapping("/users/{id}")
    public UserEntity getUser(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/users/{id}")
    public UserEntity saveUser(@PathVariable("id") Long id, @RequestBody UserRequest request) {
        return userService.updateUser(id, request);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
    }

    @GetMapping("/users/age/{age}")
    public List<UserEntity> getAllUserByAge(@PathVariable("age") int age) {
        return userService.findAllByAge(age);
    }
}
