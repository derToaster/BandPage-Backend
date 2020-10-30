package com.example.bandproject.demo.repositories;

import com.example.bandproject.demo.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository <Role, Long> {
}
