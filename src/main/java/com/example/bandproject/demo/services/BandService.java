package com.example.bandproject.demo.services;

import com.example.bandproject.demo.models.Band;
import com.example.bandproject.demo.repositories.BandRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BandService {

    private BandRepository bandRepository;


    public BandService(BandRepository bandRepository) {
        this.bandRepository = bandRepository;
    }

    public Band createBand(Band band) {
        return bandRepository.save(band);
    }

    public String deleteBand(Long bandId) {
        bandRepository.deleteById(bandId);
        return "Band Deleted";
    }

    public Page<Band> getAllBands(Pageable pageable) {
        return bandRepository.findAll(pageable);
    }

    public List<Band> getAllBandsByOwners(Long ownerId) {
        return bandRepository.findBandsByOwnerId(ownerId);
    }

    public Band getBandbyId(Long bandId) {
        return bandRepository.findById(bandId).get();
    }

    public Page<Band> search(Pageable pageable, String keyword) {
        return bandRepository.findBandsByNameContaining(keyword, pageable);
    }

    public List<Band> soerenBands() throws NullPointerException{
        List<Band> sörenBands = bandRepository.findBandsByOwnerUsernameContaining("sör");
        List<Band> soerenBands = bandRepository.findBandsByOwnerUsernameContaining("soer");


        sörenBands.addAll(soerenBands);
        if (sörenBands.isEmpty()){

            throw new NullPointerException("Array is empty");
        }else {
            return sörenBands;
        }
    }

    public String deleteSören() throws NullPointerException {
        List<Band> sörenBands = bandRepository.findBandsByOwnerUsernameContaining("sör");
        List<Band> soerenBands = bandRepository.findBandsByOwnerUsernameContaining("soer");

        sörenBands.addAll(soerenBands);
        if (sörenBands.isEmpty()) {
            throw new NullPointerException("Array is Empty");
        } else {
            bandRepository.deleteInBatch(sörenBands);
            return "All Sörenbands Deleted";
        }
    }

}
