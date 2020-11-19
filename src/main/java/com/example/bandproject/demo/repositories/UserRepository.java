package com.example.bandproject.demo.repositories;

import com.example.bandproject.demo.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {



    @Query("SELECT p FROM User p WHERE CONCAT(p.username, p.email ) LIKE %?1%")
    public Page<User> search(String keyword, Pageable pageable);

    Optional<User> findUserByUsername(String username);

}

