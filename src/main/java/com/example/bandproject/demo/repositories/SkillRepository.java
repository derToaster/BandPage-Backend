package com.example.bandproject.demo.repositories;

import com.example.bandproject.demo.models.Skills;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skills, Long> {
}
