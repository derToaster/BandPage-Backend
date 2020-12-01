package com.example.bandproject.demo.services;

import com.example.bandproject.demo.models.CheckPassword;
import com.example.bandproject.demo.models.UpdateUser;
import com.example.bandproject.demo.models.User;
import com.example.bandproject.demo.models.VerifySecurityAnswer;
import com.example.bandproject.demo.repositories.UserRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepository userRepository;

    ////////////////////////////Update User////////////////////////
    @Test
    void updateUser() throws Exception {
        UpdateUser updateUser = new UpdateUser(3L, "newPassword", "new@email.com");
        userService.updateUser(updateUser);
        User user = userRepository.findById(3L).get();
        assertEquals("new@email.com", user.getEmail());
        assertTrue(bCryptPasswordEncoder.matches("newPassword", user.getPassword()));
    }

    @Test
    void updateUser_exception() {
        UpdateUser updateUser = new UpdateUser(312L, "newPassword", "new@email.com");
        assertThrows(NotFoundException.class, () -> userService.updateUser(updateUser));

    }

    ////////////////////////////Check Password////////////////////////

    @Test
    void checkPw() throws NotFoundException {
        CheckPassword checkPassword = new CheckPassword(4L, "admin");
        assertTrue(userService.checkPw(checkPassword));
        CheckPassword checkWrongPassword = new CheckPassword(4L, "newWrongPassword");
        assertFalse(userService.checkPw(checkWrongPassword));

    }


    @Test
    void checkPw_exception() {
        CheckPassword checkPassword = new CheckPassword(233L, "user");
        assertThrows(NotFoundException.class, () -> userService.checkPw(checkPassword));
    }

    ////////////////////////////AddUser////////////////////////


    @Test
    void addUser() {
        User user = new User("TestUser", "TestPassword", "Are You Stupid ?", "Yep");
        userService.addUser(user);
        Optional<User> newUser = userRepository.findUserByUsername("TestUser");
        System.out.println(newUser.get());
        assertTrue(newUser.isPresent());
        assertTrue(bCryptPasswordEncoder.matches("TestPassword", newUser.get().getPassword()));
        assertEquals("Are You Stupid ?", newUser.get().getSecurityQuestion());
        assertTrue(bCryptPasswordEncoder.matches("Yep", newUser.get().getSecurityAnswer()));
    }

    ////////////////////////////GetByUsername////////////////////////


    @Test
    void geUserByUsername() throws NotFoundException {
        assertTrue(userService.getUserByUsername("user").isPresent());
        assertEquals("user", userService.getUserByUsername("user").get().getUsername());
    }

    @Test
    void geUserByUsername_Exception() {
        assertThrows(NotFoundException.class, () -> userService.getUserByUsername("snowden"));
    }

    ////////////////////////////VerifyAnswer////////////////////////
    @Test
    void verifyAnswer() throws NotFoundException {
        VerifySecurityAnswer verifySecurityAnswer = new VerifySecurityAnswer(3L, "yep");
        VerifySecurityAnswer verifySecurityAnswerFail = new VerifySecurityAnswer(3L, "nope");
        assertTrue(userService.verifyAnswer(verifySecurityAnswer));
        assertFalse(userService.verifyAnswer(verifySecurityAnswerFail));
    }

    @Test
    void verifyAnswer_Exception() {
        VerifySecurityAnswer verifySecurityAnswer = new VerifySecurityAnswer(323L, "yep");
        assertThrows(NotFoundException.class, () -> userService.verifyAnswer(verifySecurityAnswer));
    }

    ////////////////////////////ApproveUser////////////////////////
    @Test
    void approveUser() throws NotFoundException {
        assertFalse(userRepository.findById(3L).get().getApproved());
        userService.approveUser(3L);
        assertTrue(userRepository.findById(3L).get().getApproved());
    }

    @Test
    void approveUser_Exception() {
        assertThrows(NotFoundException.class, () -> userService.approveUser(123L));
    }

    ////////////////////////////IsApproved////////////////////////
    @Test
    void isApproved() throws NotFoundException {
        assertTrue(userService.isApproved("admin"));
        assertFalse(userService.isApproved("user"));
    }

    @Test
    void isApproved_Exception() {
        assertThrows(NotFoundException.class, () -> userService.isApproved("blabla"));

    }

    ////////////////////////////IsAdmin////////////////////////
    @Test
    void isAdmin() throws NotFoundException {
        assertTrue(userService.isAdmin("admin"));
        assertFalse(userService.isAdmin("user"));
    }

    @Test
    void isAdmin_Exception() {
        assertThrows(NotFoundException.class, () -> userService.isAdmin("blabla"));

    }

    ////////////////////////////IsRegistered////////////////////////
    @Test
    void isRegistered() {
        assertTrue(userService.isRegistered("user"));
        assertFalse(userService.isRegistered("blabla"));
    }
}
