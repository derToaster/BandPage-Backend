package com.example.bandproject.demo.services;

import com.example.bandproject.demo.models.*;
import com.example.bandproject.demo.repositories.InstrumentRepository;
import com.example.bandproject.demo.repositories.SkillLevelRepository;
import com.example.bandproject.demo.repositories.SkillRepository;
import com.example.bandproject.demo.repositories.UserRepository;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {

    private SkillLevelRepository skillLevelRepository;
    private SkillRepository skillRepository;
    private InstrumentRepository instrumentRepository;
    private UserRepository userRepository;


    public SkillService(SkillLevelRepository skillLevelRepository, SkillRepository skillRepository,
                        InstrumentRepository instrumentRepository, UserRepository userRepository) {
        this.skillLevelRepository = skillLevelRepository;
        this.skillRepository = skillRepository;
        this.instrumentRepository = instrumentRepository;
        this.userRepository = userRepository;
    }

    public List<SkillLevels> getSkilllevels() {

        return skillLevelRepository.findAll();
    }

    public Skills newSkill(AddSkill addSkill) throws NotFoundException {
        if (userRepository.findById(addSkill.getUserId()).isPresent()
                && instrumentRepository.findById(addSkill.getInstrumentId()).isPresent()
                && skillLevelRepository.findById(addSkill.getSkillLevelId()).isPresent()) {

            User user = userRepository.findById(addSkill.getUserId()).get();
            Instruments instrument = instrumentRepository.findById(addSkill.getInstrumentId()).get();
            SkillLevels skillLevels = skillLevelRepository.findById(addSkill.getSkillLevelId()).get();

            Skills skills = new Skills(instrument, user, skillLevels);

            return skillRepository.save(skills);
        }else {
            throw new NotFoundException("User not found");
        }
    }

    public void deleteOneSkill(Long skillId) {
        skillRepository.deleteById(skillId);
    }
}
