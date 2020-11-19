package com.example.bandproject.demo.repositories;

import com.example.bandproject.demo.models.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notifications, Long> {
}
