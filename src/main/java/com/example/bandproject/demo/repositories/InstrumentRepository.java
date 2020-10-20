package com.example.bandproject.demo.repositories;

import com.example.bandproject.demo.models.Instruments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstrumentRepository extends JpaRepository<Instruments, Long> {
}
