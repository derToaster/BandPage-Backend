package com.example.bandproject.demo.services;

import com.example.bandproject.demo.models.AddMembership;
import com.example.bandproject.demo.models.BandMembership;
import com.example.bandproject.demo.repositories.BandMembershipRepository;
import com.example.bandproject.demo.repositories.BandRepository;
import com.example.bandproject.demo.repositories.UserRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BandMembershipServiceTest {

    @Autowired
    private BandMembershipRepository bandMembershipRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BandRepository bandRepository;
    @Autowired
    private BandMembershipService bandMembershipService;
    /////////////////NewMembership//////////////////////
    @Test
    void NewMembership() throws NotFoundException {
        AddMembership addMembership = new AddMembership(3L,1L);
        BandMembership membership = bandMembershipService.newMembership(addMembership);
        assertEquals("user", userRepository.findById(membership.getMembers().getId()).get().getUsername());
        assertEquals("Toaster Band", bandRepository.findById(membership.getBand().getId()).get().getName());
        AddMembership addMembershipFalseMember = new AddMembership(3423L,1L);
        assertThrows(NotFoundException.class, () -> bandMembershipService.newMembership(addMembershipFalseMember));
        AddMembership addMembershipFalseBand = new AddMembership(3L,1123123L);
        assertThrows(NotFoundException.class, () -> bandMembershipService.newMembership(addMembershipFalseBand));
    }

    /////////////////DeleteMembership//////////////////////
    @Test
    void DeleteMembership() throws NotFoundException {
        AddMembership addMembership = new AddMembership(3L,1L);
        BandMembership membership = bandMembershipService.newMembership(addMembership);
        assertTrue(bandMembershipRepository.findById(membership.getId()).isPresent());
        bandMembershipService.deleteMembership(membership.getId());
        assertFalse(bandMembershipRepository.findById(membership.getId()).isPresent());
        assertThrows(NotFoundException.class, () -> bandMembershipService.deleteMembership(123L));

    }

}
