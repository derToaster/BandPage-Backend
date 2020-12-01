package com.example.bandproject.demo.controllers;

import com.example.bandproject.demo.models.AddMembership;
import com.example.bandproject.demo.models.BandMembership;
import com.example.bandproject.demo.repositories.BandMembershipRepository;
import com.example.bandproject.demo.repositories.BandRepository;
import com.example.bandproject.demo.repositories.UserRepository;
import com.example.bandproject.demo.services.BandMembershipService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/members")
public class BandMembershipController {

    @Autowired
    BandMembershipService bandMembershipService;

    @PostMapping
    public void newBandMembership(@RequestBody AddMembership addMembership) throws NotFoundException {
        bandMembershipService.newMembership(addMembership);
    }

    @DeleteMapping("{membershipId}")
    public void deleteMembership(@PathVariable(name = "membershipId") Long membershipId) throws NotFoundException {
        bandMembershipService.deleteMembership(membershipId);
    }

    @GetMapping
    public List<BandMembership> getAllMemberships() {
        return bandMembershipService.getAllMemberships();
    }
}
