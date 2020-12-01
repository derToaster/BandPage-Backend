package com.example.bandproject.demo.services;

import com.example.bandproject.demo.models.Band;
import com.example.bandproject.demo.repositories.BandRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BandServiceTest {

    @Autowired
    private BandService bandService;
    @Autowired
    private BandRepository bandRepository;

    private Band band;



    /////////////////CreateBand//////////////////////
    @Test
    void CreateBand() {
        Band band = new Band("newBand","TestRock", 12, false );
        Band newBand = bandService.createBand(band);
        assertTrue(bandRepository.findById(newBand.getId()).isPresent());
        assertEquals("newBand", bandRepository.findById(newBand.getId()).get().getName());
        assertEquals("TestRock", bandRepository.findById(newBand.getId()).get().getGenre());
        assertEquals(12, bandRepository.findById(newBand.getId()).get().getBandSize());
        assertFalse(bandRepository.findById(newBand.getId()).get().isSoeren());
    }


    /////////////////SoerenBands//////////////////////
    @Test
    void SoerenBands() {
       bandService.deleteSören();
       assertThrows(NullPointerException.class, ()-> bandService.soerenBands());
    }

    /////////////////DeleteSören//////////////////////
    @Test
    void DeleteSören() {
        /*if executing this test only pls use the deletesören method before asserting the exception*/
        assertThrows(NullPointerException.class, ()-> bandService.deleteSören());

    }
}
