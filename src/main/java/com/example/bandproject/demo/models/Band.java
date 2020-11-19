package com.example.bandproject.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Band {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "band_id")
    Long id;
    String name;
    String genre;
    Integer bandSize;
    boolean isSoeren = false;

    @ManyToOne
    @JsonIgnoreProperties(value = {"ownership", "sent", "received", "memberships"}, allowSetters = true)
    User owner;

    @OneToMany(mappedBy = "band")
    Set<BandMembership> memberships = new HashSet<>();


    @OneToOne
    Notifications bandInvites;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }


    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getBandSize() {
        return bandSize;
    }

    public void setBandSize(Integer bandSize) {
        this.bandSize = bandSize;
    }

    public Notifications getBandInvites() {
        return bandInvites;
    }

    public void setBandInvites(Notifications bandInvites) {
        this.bandInvites = bandInvites;
    }

    public Set<BandMembership> getMemberships() {
        return memberships;
    }

    public void setMemberships(Set<BandMembership> memberships) {
        this.memberships = memberships;
    }

    public boolean isSoeren() {
        return isSoeren;
    }

    public void setSoeren(boolean soeren) {
        isSoeren = soeren;
    }
}
