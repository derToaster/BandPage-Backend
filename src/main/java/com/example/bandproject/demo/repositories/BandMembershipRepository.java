package com.example.bandproject.demo.repositories;

import com.example.bandproject.demo.models.BandMembership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BandMembershipRepository extends JpaRepository<BandMembership, Long> {
}
