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

    public void updateUser(UpdateUser user) throws NotFoundException {
        if (userRepository.findById(user.getId()).isPresent()) {
            Optional<User> currentUser = userRepository.findById(user.getId());
            currentUser.get().setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            currentUser.get().setEmail(user.getEmail());
            userRepository.save(currentUser.get());
        }else{
            throw new NotFoundException("this User was not Found");
        }
    }

    public boolean checkPw(CheckPassword checkPassword) throws NotFoundException {
        if (userRepository.findById(checkPassword.getId()).isPresent()) {
            User user = userRepository.findById(checkPassword.getId()).get();

            if (bCryptPasswordEncoder.matches(checkPassword.getPassword(), user.getPassword())) {
                return true;
            } else {
                return false;
            }
        }
        throw new NotFoundException("User with this ID not found");
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

    public Optional<User> getUserByUsername(String username) throws NotFoundException {
        if (userRepository.findUserByUsername(username).isPresent()) {
            return userRepository.findUserByUsername(username);
        } else {
            throw new NotFoundException("User with username " + username + "not found");
        }
    }

    public boolean verifyAnswer(VerifySecurityAnswer verifySecurityAnswer) throws NotFoundException {
        if (userRepository.findById(verifySecurityAnswer.getUserId()).isPresent()) {
            User user = userRepository.findById(verifySecurityAnswer.getUserId()).get();
            return bCryptPasswordEncoder.matches(verifySecurityAnswer.getAnswer(), user.getSecurityAnswer());
        }else  {
            throw new NotFoundException("User not Found");

        }
    }

    public String approveUser(Long userId) throws NotFoundException {
        if (userRepository.findById(userId).isPresent()) {
            User user = userRepository.findById(userId).get();
            user.setApproved(true);
            userRepository.save(user);
            return user.getUsername() + "was approved";
        }else{
            throw new NotFoundException("User not found");
        }

    }

    public boolean isApproved(String username) throws NotFoundException {
        if (userRepository.findUserByUsername(username).isPresent()) {
            User user = userRepository.findUserByUsername(username).get();
            return user.getApproved();
        } else {
            throw new NotFoundException("User not found");
        }
    }

    public boolean isAdmin(String username) throws NotFoundException {
        if (userRepository.findUserByUsername(username).isPresent()) {
            User user = userRepository.findUserByUsername(username).get();
            for (Role role : user.getRole()) {
                if (role.getName().equals("ADMIN")) {
                    return true;
                }
            }
            return false;
        }else {
            throw new NotFoundException("User not found");
        }
    }
    public boolean isRegistered(String username){
       return userRepository.findUserByUsername(username).isPresent();
    }
}
