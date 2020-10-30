package com.example.bandproject.demo.controllers;


import com.example.bandproject.demo.models.Instruments;
import com.example.bandproject.demo.models.SkillLevels;
import com.example.bandproject.demo.models.Skills;
import com.example.bandproject.demo.models.User;
import com.example.bandproject.demo.models.AddSkill;
import com.example.bandproject.demo.repositories.InstrumentRepository;
import com.example.bandproject.demo.repositories.SkillLevelRepository;
import com.example.bandproject.demo.repositories.SkillRepository;
import com.example.bandproject.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sound.midi.Instrument;
import java.util.List;

@RestController
@RequestMapping("/api/v1/skill")
public class SkillController {

    @Autowired
    private SkillLevelRepository skillLevelRepository;
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private InstrumentRepository instrumentRepository;
    @Autowired
    private UserRepository userRepository;


    @GetMapping
    public List<SkillLevels> getAllSkilllevels(){
        return skillLevelRepository.findAll();
    }


    @PostMapping
    public void newSkill (@RequestBody AddSkill addSkill){
        User user = userRepository.findById(addSkill.getUserId()).get();
        Instruments instrument = instrumentRepository.findById(addSkill.getInstrumentId()).get();
        SkillLevels skillLevels = skillLevelRepository.findById(addSkill.getSkillLevelId()).get();

        Skills skills = new Skills(instrument, user, skillLevels);

        skillRepository.save(skills);
    }
    @DeleteMapping("/{id}")
    public void deleteOne(@PathVariable("id") long id) {
        skillRepository.deleteById(id);
    }
}
