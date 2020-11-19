package com.example.bandproject.demo.controllers;

import com.example.bandproject.demo.models.Notifications;
import com.example.bandproject.demo.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/notifications")
public class NotificationController {

    @Autowired
    NotificationRepository notificationRepository;

    @PostMapping
    public Notifications createNotification (@RequestBody Notifications notifications){

        return notificationRepository.save(notifications);
    }
    @DeleteMapping("/{notificationId}")
    public String deleteNotification(@PathVariable("notificationId") Long notificationId){
        notificationRepository.deleteById(notificationId);
        return "notification Deleted";
    }
}
