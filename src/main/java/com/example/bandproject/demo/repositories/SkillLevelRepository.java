package com.example.bandproject.demo.repositories;

import com.example.bandproject.demo.models.SkillLevels;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillLevelRepository extends JpaRepository<SkillLevels, Long> {
}
