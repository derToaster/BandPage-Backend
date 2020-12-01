package com.example.bandproject.demo.services;

import com.example.bandproject.demo.models.AddMembership;
import com.example.bandproject.demo.models.Band;
import com.example.bandproject.demo.models.BandMembership;
import com.example.bandproject.demo.models.User;
import com.example.bandproject.demo.repositories.BandMembershipRepository;
import com.example.bandproject.demo.repositories.BandRepository;
import com.example.bandproject.demo.repositories.UserRepository;
import javassist.NotFoundException;
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

    public BandMembership newMembership(AddMembership addMembership) throws NotFoundException {
        if (userRepository.findById(addMembership.getMemberId()).isPresent() && bandRepository.findById(addMembership.getBandId()).isPresent()) {
            User user = userRepository.findById(addMembership.getMemberId()).get();
            Band band = bandRepository.findById(addMembership.getBandId()).get();

            BandMembership bandMembership = new BandMembership(user, band);

           return bandMembershipRepository.save(bandMembership);
        } else {
            throw new NotFoundException(" Band or User Not found");
        }
    }

    public void deleteMembership(Long membershipId) throws NotFoundException {

        if (bandMembershipRepository.findById(membershipId).isPresent()) {
            bandMembershipRepository.deleteById(membershipId);
        } else {
            throw new NotFoundException("this membership does not exist");
        }
    }

    public List<BandMembership> getAllMemberships() {
        return bandMembershipRepository.findAll();
    }
}
