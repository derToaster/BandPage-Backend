package com.example.bandproject.demo.controllers;

import com.example.bandproject.demo.models.CheckPassword;
import com.example.bandproject.demo.models.UpdateUser;
import com.example.bandproject.demo.models.User;
import com.example.bandproject.demo.models.VerifySecurityAnswer;
import com.example.bandproject.demo.repositories.UserRepository;
import com.example.bandproject.demo.services.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin("http://localhost:8089")
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    UserService userService;

    @GetMapping("/")
    public Page<User> getUser(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @GetMapping("/search/{keyword}")
    public Page<User> search(@PathVariable("keyword") String keyword, Pageable pageable) {
        return userRepository.search(keyword, pageable);
    }


    @PostMapping
    public void create(@RequestBody User user) {
        userRepository.save(user);

    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") long id) {
        return userRepository.findById(id).get();

    }

    @DeleteMapping("/{id}")
    public void deleteOne(@PathVariable("id") long id) {
        userRepository.deleteById(id);
    }

    @GetMapping("/page")
    public Page<User> showPage(@RequestParam(defaultValue = "0") int page) {
        return userRepository.findAll(PageRequest.of(page, 4));
    }

    @PutMapping
    public void updateUser(@RequestBody UpdateUser user) {
        userService.updateUser(user);
    }

    @GetMapping("/all")
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @PostMapping("/checkpw")
    public boolean checkPassword(@RequestBody CheckPassword checkPassword) {

        return userService.checkPw(checkPassword);
    }


    @PostMapping("/add")
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @GetMapping("/get/{username}")
    public User getByUsername(@PathVariable("username") String username) throws NotFoundException {
        return userService.geUserByUsername(username);
    }

    @GetMapping("/admin")
    public String admin() {
        return "Admin";
    }

    @GetMapping("/user")
    public String user() {
        return "user";
    }

    @PostMapping("/verifyanswer")
    public boolean verifyAnswer(@RequestBody VerifySecurityAnswer verifySecurityAnswer) {
        return userService.verifyAnswer(verifySecurityAnswer);
    }

    @PostMapping("/approve/{userId}")
    public String approveUser(@PathVariable("userId") Long userId) {
        return userService.approveUser(userId);
    }

    @GetMapping("/approved/{username}")
    public boolean isApproved(@PathVariable("username") String username) {
        return userService.isApproved(username);
    }

    @GetMapping("/isAdmin/{username}")
    public boolean isAdmin(@PathVariable("username") String username) {
        return userService.isAdmin(username);
    }
    @GetMapping("/isRegistered/{username}")
    public boolean isRegistered(@PathVariable("username") String username){
        return userService.isRegistered(username);
    }
}



