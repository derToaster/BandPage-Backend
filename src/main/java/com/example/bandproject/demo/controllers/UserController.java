package com.example.bandproject.demo.controllers;

import com.example.bandproject.demo.models.User;
import com.example.bandproject.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
   private UserRepository userRepository;

    @GetMapping
    public List<User> getUsers (){
        return userRepository.findAll();
    }

    @PostMapping
    public void create(@RequestBody User user) {
        userRepository.save(user);

    }

    @GetMapping("/{id}")
    public User getOne(@PathVariable("id")long id){
        return userRepository.getOne(id);
    }

    @DeleteMapping("/{id}")
    public void deleteOne(@PathVariable("id") long id){
        userRepository.deleteById(id);
    }

}
