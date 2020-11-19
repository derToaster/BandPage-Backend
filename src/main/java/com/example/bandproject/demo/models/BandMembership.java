package com.example.bandproject.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;

import javax.persistence.*;

@Entity
@JsonIgnoreType
public class BandMembership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_membership_id")
    @JsonIgnoreProperties(value = {"memberships", "ownership", "sent", "received"}, allowSetters = true)
    User members;

    @ManyToOne
    @JoinColumn(name = "band_membership_id")
    @JsonIgnoreProperties(value = "memberships", allowSetters = true)
    Band band;

    public BandMembership() {
    }

    public BandMembership(User members, Band bands) {
        this.members = members;
        this.band = bands;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getMembers() {
        return members;
    }

    public void setMembers(User members) {
        this.members = members;
    }


    public Band getBand() {
        return band;
    }

    public void setBand(Band band) {
        this.band = band;
    }
}
