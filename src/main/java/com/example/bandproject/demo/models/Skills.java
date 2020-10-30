package com.example.bandproject.demo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Skills {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skills_id")
    private Long id;

//    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "instrument_id")
    private Instruments instruments;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User users;

//    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "skilllevel_id")
    private SkillLevels skillLevels;


    public Skills(Instruments instruments, User users, SkillLevels skillLevels) {
        this.instruments = instruments;
        this.users = users;
        this.skillLevels = skillLevels;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instruments getInstruments() {
        return instruments;
    }

    public void setInstruments(Instruments instruments) {
        this.instruments = instruments;
    }

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }

    public SkillLevels getSkillLevels() {
        return skillLevels;
    }

    public void setSkillLevels(SkillLevels skillLevels) {
        this.skillLevels = skillLevels;
    }
}
