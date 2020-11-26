package com.example.bandproject.demo.services;

import com.example.bandproject.demo.models.EmailCfg;
import com.example.bandproject.demo.models.Mail;
import com.example.bandproject.demo.models.User;
import com.example.bandproject.demo.repositories.UserRepository;
import javassist.NotFoundException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.xml.bind.ValidationException;
import java.util.Random;

@Service
public class MailService {

    private EmailCfg emailCfg;
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public MailService(EmailCfg emailCfg, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.emailCfg = emailCfg;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void sendMail(Mail mail, BindingResult bindingResult) throws ValidationException {
            if (bindingResult.hasErrors()){
                throw new ValidationException("Mail not Valid");
            }
            JavaMailSenderImpl mailSender = getJavaMailSender();

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("info@bandpage.com");
            mailMessage.setTo(mail.getEmail());
            mailMessage.setSubject(mail.getSubject());
            mailMessage.setText(mail.getMessage());

            mailSender.send(mailMessage);

        }
    private JavaMailSenderImpl getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(emailCfg.getHost());
        mailSender.setPort(emailCfg.getPort());
        mailSender.setUsername(emailCfg.getUsername());
        mailSender.setPassword(emailCfg.getPassword());
        return mailSender;
    }

    public void resetPassword(Mail mail, BindingResult bindingResult) throws ValidationException, NotFoundException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException("Mail not Valid");
        }
        if (userRepository.findUserByUsername(mail.getName()).isPresent()) {
            User user = userRepository.findUserByUsername(mail.getName()).get();
            String newPassword = alphaNumericString(24);
            user.setPassword(bCryptPasswordEncoder.encode(newPassword));
            userRepository.save(user);

            JavaMailSenderImpl mailSender = getJavaMailSender();

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("info@bandpage.com");
            mailMessage.setTo(mail.getEmail());
            mailMessage.setSubject(mail.getSubject());
            mailMessage.setText(mail.getMessage() + newPassword);

            mailSender.send(mailMessage);
        } else {
            throw new NotFoundException("The user does not Exist");
        }
    }
    public static String alphaNumericString(int len) {
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();

        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }
}
