package com.example.bandproject.demo.services;

import com.example.bandproject.demo.models.*;
import com.example.bandproject.demo.repositories.RoleRepository;
import com.example.bandproject.demo.repositories.UserRepository;
import javassist.NotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {


    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private RoleRepository roleRepository;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
    }

    public void updateUser(UpdateUser user) {
        Optional<User> currentUser = userRepository.findById(user.getId());
        currentUser.get().setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        currentUser.get().setEmail(user.getEmail());
        userRepository.save(currentUser.get());
    }

    public boolean checkPw(CheckPassword checkPassword) {
        User user = userRepository.findById(checkPassword.getId()).get();

        if (bCryptPasswordEncoder.matches(checkPassword.getPassword(), user.getPassword())) {
            return true;
        } else {
            return false;
        }

    }

    public User addUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setSecurityAnswer(bCryptPasswordEncoder.encode(user.getSecurityAnswer()));
        user.setApproved(false);
        Set<Role> role = new HashSet<>();
        role.add(roleRepository.findById(1L).get());
        user.setRole(role);
        user.setEnabled(true);
        return userRepository.save(user);
    }

    public User geUserByUsername(String username) throws NotFoundException {
        if (userRepository.findUserByUsername(username).isPresent()) {
            return userRepository.findUserByUsername(username).get();
        } else {
            throw new NotFoundException("User with username " + username + "not found");
        }
    }

    public boolean verifyAnswer(VerifySecurityAnswer verifySecurityAnswer) {
        User user = userRepository.findById(verifySecurityAnswer.getUserId()).get();
        return bCryptPasswordEncoder.matches(verifySecurityAnswer.getAnswer(), user.getSecurityAnswer());
    }

    public String approveUser(Long userId) {
        User user = userRepository.findById(userId).get();
        user.setApproved(true);
        userRepository.save(user);
        return user.getUsername() + "was approved";
    }

    public boolean isApproved(String username) {
        User user = userRepository.findUserByUsername(username).get();
        return user.getApproved();
    }

    public boolean isAdmin(String username) {
        User user = userRepository.findUserByUsername(username).get();
        for (Role role : user.getRole()) {
            if (role.getName().equals("ADMIN")) {
                return true;
            }
        }
        return false;
    }
    public boolean isRegistered(String username){
       return userRepository.findUserByUsername(username).isPresent();
    }
}
