package com.example.bandproject.demo.controllers;

import com.example.bandproject.demo.models.Band;
import com.example.bandproject.demo.models.User;
import com.example.bandproject.demo.repositories.BandRepository;
import com.example.bandproject.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/bands")
public class BandController {

    @Autowired
    private BandRepository bandRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public Band createBand(@RequestBody Band band) {
        return bandRepository.save(band);
    }

    @DeleteMapping("/{band}")
    public String deleteBand(@PathVariable("band") Long bandId) {
        bandRepository.deleteById(bandId);
        return "Band Deleted";
    }

    @GetMapping("/")
    public Page<Band> getAllBands(Pageable pageable) {

        return bandRepository.findAll(pageable);
    }

    @GetMapping("/bandowners/{owner}")
    public List<Band> getAllBandsByOwners(@PathVariable("owner") Long ownerId){
        return bandRepository.findBandsByOwnerId(ownerId);
    }
    @GetMapping("{id}")
    public Band getBandById(@PathVariable("id") Long id){
        return bandRepository.findById(id).get();
    }

    @GetMapping("/search/{keyword}")
    public Page<Band> search(@PathVariable("keyword") String keyword, Pageable pageable) {
        return bandRepository.findBandsByNameContaining(keyword, pageable);
    }
    @GetMapping("/isSoeren")
    public List<Band> soerenBands(){
        List<Band> sörenBands = bandRepository.findBandsByOwnerUsernameContaining("sör");
        List<Band> soerenBands = bandRepository.findBandsByOwnerUsernameContaining("soer");

        sörenBands.addAll(soerenBands);
        return sörenBands;
    }
}
