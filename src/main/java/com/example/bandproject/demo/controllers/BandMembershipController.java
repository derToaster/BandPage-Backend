package com.example.bandproject.demo.controllers;

import com.example.bandproject.demo.models.AddMembership;
import com.example.bandproject.demo.models.Band;
import com.example.bandproject.demo.models.BandMembership;
import com.example.bandproject.demo.models.User;
import com.example.bandproject.demo.repositories.BandMembershipRepository;
import com.example.bandproject.demo.repositories.BandRepository;
import com.example.bandproject.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/members")
public class BandMembershipController {

    @Autowired
    BandMembershipRepository bandMembershipRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BandRepository bandRepository;


    @PostMapping
    public void newSkill(@RequestBody AddMembership addMembership) {
        User user = userRepository.findById(addMembership.getMemberId()).get();
        Band band = bandRepository.findById(addMembership.getBandId()).get();

        BandMembership bandMembership = new BandMembership(user, band);

        bandMembershipRepository.save(bandMembership);
    }

    @DeleteMapping("{membershipId}")
    public void deleteMembership(@PathVariable(name = "membershipId") Long membershipId) {
        bandMembershipRepository.deleteById(membershipId);
    }
    @GetMapping
    public List<BandMembership> getAllMemberships(){
        return bandMembershipRepository.findAll();
    }
}
