package com.example.bandproject.demo.controllers;


import com.example.bandproject.demo.models.AddSkill;
import com.example.bandproject.demo.models.SkillLevels;
import com.example.bandproject.demo.repositories.InstrumentRepository;
import com.example.bandproject.demo.repositories.SkillLevelRepository;
import com.example.bandproject.demo.repositories.SkillRepository;
import com.example.bandproject.demo.repositories.UserRepository;
import com.example.bandproject.demo.services.SkillService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/skill")
public class SkillController {


    @Autowired
    private SkillService skillService;


    @GetMapping
    public List<SkillLevels> getAllSkilllevels() {
        return skillService.getSkilllevels();
    }


    @PostMapping
    public void newSkill(@RequestBody AddSkill addSkill) throws NotFoundException {
        skillService.newSkill(addSkill);
    }

    @DeleteMapping("/{id}")
    public void deleteOne(@PathVariable("id") long id) {
        skillService.deleteOneSkill(id);
    }
}
