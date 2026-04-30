package com.dreamora.service;

import com.dreamora.model.Offer;
import com.dreamora.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfferService {

    @Autowired
    private OfferRepository repository;

    // Get all offers
    public List<Offer> getAllOffers() {
        return repository.findAll();
    }

    // Get offer by ID
    public Offer getOfferById(Long id) {
        Optional<Offer> offer = repository.findById(id);
        return offer.orElse(null);
    }

    // Create new offer
    public Offer createOffer(Offer offer) {
        return repository.save(offer);
    }

    // Update offer
    public Offer updateOffer(Long id, Offer offer) {
        Optional<Offer> existingOffer = repository.findById(id);
        if (existingOffer.isPresent()) {
            Offer o = existingOffer.get();
            o.setName(offer.getName());
            o.setDescription(offer.getDescription());
            o.setDiscountPercentage(offer.getDiscountPercentage());
            o.setStartDate(offer.getStartDate());
            o.setEndDate(offer.getEndDate());
            o.setMenuItem(offer.getMenuItem());
            return repository.save(o);
        } else {
            return null;
        }
    }

    // Delete offer
    public void deleteOffer(Long id) {
        repository.deleteById(id);
    }
}
