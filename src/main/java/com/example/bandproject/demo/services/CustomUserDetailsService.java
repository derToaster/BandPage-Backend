package com.example.bandproject.demo.services;

import com.example.bandproject.demo.models.CustomUserDetails;
import com.example.bandproject.demo.models.User;
import com.example.bandproject.demo.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = this.userRepository.findUserByUsername(s).get();
        CustomUserDetails userDetails = new CustomUserDetails(user);

        return userDetails;
    }
}
