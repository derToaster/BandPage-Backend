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
public class Instruments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "instrument_id")
    long id;
    String name;
    @JsonBackReference
    @OneToMany(mappedBy ="instruments", cascade = CascadeType.MERGE)
    private Set<Skills> instrumentSkill = new HashSet<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Skills> getInstrumentSkill() {
        return instrumentSkill;
    }

    public void setInstrumentSkill(Set<Skills> instrumentSkill) {
        this.instrumentSkill = instrumentSkill;
    }
}
