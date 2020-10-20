package com.example.bandproject.demo.controllers;

import com.example.bandproject.demo.models.User;
import com.example.bandproject.demo.repositories.UserRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping
    public Page<User> getUser(Pageable pageable){
        return userRepository.findAll(pageable);
    }

//    @GetMapping
//    public Page<User> getUsers(@RequestParam(defaultValue = "id") String sortParam,
//                               @RequestParam(defaultValue = "true") boolean sortDirection,
//                               @RequestParam(defaultValue = "0") Integer page,
//                               @RequestParam(defaultValue = "false") boolean showAll,
//                               @RequestParam(defaultValue = "4") Integer itemsPerList,
//                               @Param("keyword") String keyword) {
//        var direction = sortDirection ? Sort.Direction.ASC : Sort.Direction.DESC;
//        var itemAmount = showAll ? 10000 : itemsPerList;
//
//        return userRepository.search(keyword, PageRequest.of(page, itemAmount, Sort.by(direction, sortParam)));
//
//    }

    @PostMapping
    public void create(@RequestBody User user) {
        userRepository.save(user);

    }
    @GetMapping("/{id}")
    public Optional<User> getUserbyId(@PathVariable("id") long id){
        return userRepository.findById(id);

    }
    @DeleteMapping("/{id}")
    public void deleteOne(@PathVariable("id") long id) {
        userRepository.deleteById(id);
    }

    @GetMapping("/page")
    public Page<User> showPage(@RequestParam(defaultValue = "0") int page) {
        return userRepository.findAll(PageRequest.of(page, 4));
    }
    @PutMapping("/{id}")
    public void updateUser(@RequestBody User user,
                           @PathVariable("id") long id){
        Optional<User> currentUser = userRepository.findById(id);
        currentUser.ifPresent(value -> BeanUtils.copyProperties(user, value));
    }

    @GetMapping("/all")
    public List<User> getAll(){
        return userRepository.findAll();
    }


    @PostMapping("/add")
    public User addUser(@RequestBody User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @GetMapping("/admin")
    public String admin(){
        return "Admin";
    }
    @GetMapping("/user")
    public String user(){
        return "user";
    }




}
