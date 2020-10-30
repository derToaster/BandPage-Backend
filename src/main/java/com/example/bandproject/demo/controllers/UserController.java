package com.example.bandproject.demo.controllers;

import com.example.bandproject.demo.models.*;
import com.example.bandproject.demo.repositories.RoleRepository;
import com.example.bandproject.demo.repositories.UserRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleInfo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toList;

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
    public User getUserbyId(@PathVariable("id") long id){
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
    public void updateUser(@RequestBody UpdateUser user){
        Optional<User> currentUser = userRepository.findById(user.getId());
        currentUser.get().setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        currentUser.get().setEmail(user.getEmail());
        userRepository.save(currentUser.get());
    }

    @GetMapping("/all")
    public List<User> getAll(){
        return userRepository.findAll();
    }

    @PostMapping("/checkpw")
    public boolean checkPassword(@RequestBody CheckPassword checkPassword){

       User user = userRepository.findById(checkPassword.getId()).get();
           System.out.println("frontend PW: " + bCryptPasswordEncoder.encode( checkPassword.getPassword()));
           System.out.println("backend PW: " + user.getPassword());
       if (bCryptPasswordEncoder.matches(checkPassword.getPassword(), user.getPassword())){
           return true;
       }else   {
           return false;
       }
    }


    @PostMapping("/add")
    public User addUser(@RequestBody User user){

        System.out.println( "new Userpassword " + user.getPassword());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        System.out.println( "new hashed Userpassword " + user.getPassword());
        Set<Role> role = new HashSet<>();
        role.add(roleRepository.findById(1L).get());
        user.setRole(role);
        user.setEnabled(true);
        return userRepository.save(user);
    }
    @GetMapping("/get/{username}")
    public User getByUsername(@PathVariable("username") String username){
        return userRepository.findUserByUsername(username).get();
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
