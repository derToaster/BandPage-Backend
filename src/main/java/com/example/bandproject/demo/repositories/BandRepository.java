package com.example.bandproject.demo.repositories;

import com.example.bandproject.demo.models.Band;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BandRepository extends JpaRepository<Band, Long> {
    List<Band> findBandsByOwnerId(Long Id);
    Page<Band> findBandsByNameContaining(String name, Pageable pageable);
    List<Band> findBandsByOwnerUsernameContaining(String name);
    Optional<Band> findBandByName(String name);

}
