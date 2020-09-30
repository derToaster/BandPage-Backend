package com.example.bandproject.demo.repositories;

import com.example.bandproject.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

}
