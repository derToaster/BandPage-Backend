package com.example.bandproject.demo.controllers;

import com.example.bandproject.demo.models.Instruments;
import com.example.bandproject.demo.repositories.InstrumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/instruments")
@CrossOrigin("http://localhost:8089")
public class InstrumentController {


    @Autowired
    private InstrumentRepository instrumentRepository;

    @GetMapping
    public List<Instruments> getAllInstruments (){
        return instrumentRepository.findAll();
    }


}
