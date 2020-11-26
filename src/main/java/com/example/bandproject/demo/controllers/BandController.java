package com.example.bandproject.demo.controllers;

import com.example.bandproject.demo.models.Band;
import com.example.bandproject.demo.repositories.BandRepository;
import com.example.bandproject.demo.services.BandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/bands")
public class BandController {


    @Autowired
    private BandService bandService;

    @PostMapping
    public Band createBand(@RequestBody Band band) {
        return bandService.createBand(band);
    }

    @DeleteMapping("/{band}")
    public String deleteBand(@PathVariable("band") Long bandId) {
        return bandService.deleteBand(bandId);
    }

    @GetMapping("/")
    public Page<Band> getAllBands(Pageable pageable) {

        return bandService.getAllBands(pageable);
    }

    @GetMapping("/bandowners/{owner}")
    public List<Band> getAllBandsByOwners(@PathVariable("owner") Long ownerId) {
        return bandService.getAllBandsByOwners(ownerId);
    }

    @GetMapping("{id}")
    public Band getBandById(@PathVariable("id") Long id) {
        return bandService.getBandbyId(id);
    }

    @GetMapping("/search/{keyword}")
    public Page<Band> search(@PathVariable("keyword") String keyword, Pageable pageable) {
        return bandService.search(pageable, keyword);
    }


    @GetMapping("/isSoeren")
    public List<Band> soerenBands() {

        return bandService.soerenBands();

    }


    @DeleteMapping("/isSoeren")
    public String deleteSoeren() {
       return bandService.deleteSören();
    }
}
