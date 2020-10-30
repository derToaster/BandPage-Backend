package com.example.bandproject.demo.models;



import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SkillLevels {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String skillLevel;


    @JsonBackReference
    @OneToMany(mappedBy ="skillLevels", cascade = CascadeType.MERGE)
    private Set<Skills> skillLevelSkills = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(String skillLevel) {
        this.skillLevel = skillLevel;
    }

    public Set<Skills> getSkillLevelSkills() {
        return skillLevelSkills;
    }

    public void setSkillLevelSkills(Set<Skills> skillLevelSkills) {
        this.skillLevelSkills = skillLevelSkills;
    }
}
