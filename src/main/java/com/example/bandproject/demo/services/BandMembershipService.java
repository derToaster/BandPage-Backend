package com.example.bandproject.demo.services;

import com.example.bandproject.demo.models.AddMembership;
import com.example.bandproject.demo.models.Band;
import com.example.bandproject.demo.models.BandMembership;
import com.example.bandproject.demo.models.User;
import com.example.bandproject.demo.repositories.BandMembershipRepository;
import com.example.bandproject.demo.repositories.BandRepository;
import com.example.bandproject.demo.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BandMembershipService {

    private BandMembershipRepository bandMembershipRepository;
    private UserRepository userRepository;
    private BandRepository bandRepository;

    public BandMembershipService(BandMembershipRepository bandMembershipRepository, UserRepository userRepository, BandRepository bandRepository) {
        this.bandMembershipRepository = bandMembershipRepository;
        this.userRepository = userRepository;
        this.bandRepository = bandRepository;
    }

    public void newMembership(AddMembership addMembership){
        User user = userRepository.findById(addMembership.getMemberId()).get();
        Band band = bandRepository.findById(addMembership.getBandId()).get();

        BandMembership bandMembership = new BandMembership(user, band);

        bandMembershipRepository.save(bandMembership);
    }
    public void deleteMembership(Long membershipId){
        bandMembershipRepository.deleteById(membershipId);
    }

    public List<BandMembership> getAllMemberships(){
        return bandMembershipRepository.findAll();
    }
}
