package com.example.bandproject.demo.controllers;

import com.example.bandproject.demo.models.EmailCfg;
import com.example.bandproject.demo.models.Mail;
import com.example.bandproject.demo.models.User;
import com.example.bandproject.demo.repositories.UserRepository;
import com.example.bandproject.demo.services.MailService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.ValidationException;
import java.util.Random;

@RestController
@RequestMapping("mail")
public class MailController {



    @Autowired
    private MailService mailService;

    @PostMapping
    public void sendMail(@RequestBody Mail mail,
                         BindingResult bindingResult) throws ValidationException {

        mailService.sendMail(mail, bindingResult);

    }


    @PostMapping("/resetpassword")
    public void resetPassword(@RequestBody Mail mail, BindingResult bindingResult) throws ValidationException, NotFoundException {
        mailService.resetPassword(mail, bindingResult);
    }


}
