package com.example.bandproject.demo.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.bytebuddy.build.ToStringPlugin;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Instruments {

    @Id
    @GeneratedValue
    long id;
    String name;





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

}
