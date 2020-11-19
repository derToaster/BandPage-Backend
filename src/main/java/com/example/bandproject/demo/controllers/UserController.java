package com.example.bandproject.demo.controllers;

import com.example.bandproject.demo.models.*;
import com.example.bandproject.demo.repositories.RoleRepository;
import com.example.bandproject.demo.repositories.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin("http://localhost:8089")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleRepository roleRepository;

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
    public User getUserbyId(@PathVariable("id") long id) {
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
        Optional<User> currentUser = userRepository.findById(user.getId());
        currentUser.get().setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        currentUser.get().setEmail(user.getEmail());
        userRepository.save(currentUser.get());
    }

    @GetMapping("/all")
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @PostMapping("/checkpw")
    public boolean checkPassword(@RequestBody CheckPassword checkPassword) {

        User user = userRepository.findById(checkPassword.getId()).get();
        System.out.println("frontend PW: " + bCryptPasswordEncoder.encode(checkPassword.getPassword()));
        System.out.println("backend PW: " + user.getPassword());
        if (bCryptPasswordEncoder.matches(checkPassword.getPassword(), user.getPassword())) {
            return true;
        } else {
            return false;
        }
    }


    @PostMapping("/add")
    public User addUser(@RequestBody User user) {

        System.out.println("new Userpassword " + user.getPassword());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        System.out.println("new hashed Userpassword " + user.getPassword());
        user.setSecurityAnswer(bCryptPasswordEncoder.encode(user.getSecurityAnswer()));
        user.setApproved(false);
        Set<Role> role = new HashSet<>();
        role.add(roleRepository.findById(1L).get());
        user.setRole(role);
        user.setEnabled(true);
        return userRepository.save(user);
    }

    @GetMapping("/get/{username}")
    public User getByUsername(@PathVariable("username") String username) throws NotFoundException {
        if (userRepository.findUserByUsername(username).isPresent()) {
            return userRepository.findUserByUsername(username).get();
        } else {
            throw new NotFoundException("User with username " + username + "not found");
        }
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
        User user = userRepository.findById(verifySecurityAnswer.getUserId()).get();
        return bCryptPasswordEncoder.matches(verifySecurityAnswer.getAnswer(), user.getSecurityAnswer());
    }

    @PostMapping("/approve/{userId}")
    public String approveUser(@PathVariable("userId") Long userId) {
        User user = userRepository.findById(userId).get();
        user.setApproved(true);
        userRepository.save(user);
        return user.getUsername() + "was approved";
    }

    @GetMapping("/approved/{username}")
    public boolean isApproved(@PathVariable("username") String username) {
        User user = userRepository.findUserByUsername(username).get();
        return user.getApproved();
    }

    @GetMapping("/isAdmin/{username}")
    public boolean isAdmin(@PathVariable("username") String username) {
        User user = userRepository.findUserByUsername(username).get();
        for (Role role : user.getRole()) {
            if (role.getName().equals("ADMIN")) {
                return true;
            }
        }
        return false;
    }
}



