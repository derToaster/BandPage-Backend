package com.example.bandproject.demo.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Skills {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String skill;




}
