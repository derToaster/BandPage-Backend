package com.example.bandproject.demo.services;

import com.example.bandproject.demo.models.AddSkill;
import com.example.bandproject.demo.models.Skills;
import com.example.bandproject.demo.repositories.SkillLevelRepository;
import com.example.bandproject.demo.repositories.SkillRepository;
import com.example.bandproject.demo.repositories.UserRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SkillServiceTest {
    @Autowired
    SkillLevelRepository skillLevelRepository;
    @Autowired
    SkillService skillService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SkillRepository skillRepository;



    /////////////////NewSkill//////////////////////
    @Test
    void NewSkill() throws NotFoundException {
        AddSkill addSkill = new AddSkill(1L, 3L, 1L);
        AddSkill addSkillFalseUser = new AddSkill(1L, 32L, 1L);
        AddSkill addSkillFalseSkillLevel = new AddSkill(1L, 3L, 123L);
        AddSkill addSkillFalseInstrument = new AddSkill(1234L, 3L, 1L);
        Skills newSkill = skillService.newSkill(addSkill);
        assertTrue(skillRepository.findById(newSkill.getId()).isPresent());
        assertThrows(NotFoundException.class, () -> skillService.newSkill(addSkillFalseInstrument));
        assertThrows(NotFoundException.class, () -> skillService.newSkill(addSkillFalseSkillLevel));
        assertThrows(NotFoundException.class, () -> skillService.newSkill(addSkillFalseUser));
    }


    /////////////////DeleteOneSkill//////////////////////
    @Test
    void DeleteOneSkill() {
        assertTrue(skillRepository.findById(1L).isPresent());
        skillService.deleteOneSkill(1L);
        assertFalse(skillRepository.findById(1L).isPresent());
    }
}
