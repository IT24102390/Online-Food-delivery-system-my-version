package com.dreamora.Controller;

import com.dreamora.model.Offer;
import com.dreamora.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offers")
@CrossOrigin
public class OfferController {

    @Autowired
    private OfferService service;

    // Get all offers
    @GetMapping
    public List<Offer> getAllOffers() {
        return service.getAllOffers();
    }

    // Get offer by ID
    @GetMapping("/{id}")
    public Offer getOfferById(@PathVariable Long id) {
        return service.getOfferById(id);
    }

    // Create new offer
    @PostMapping
    public Offer createOffer(@RequestBody Offer offer) {
        return service.createOffer(offer);
    }

    // Update offer
    @PutMapping("/{id}")
    public Offer updateOffer(@PathVariable Long id, @RequestBody Offer offer) {
        return service.updateOffer(id, offer);
    }

    // Delete offer
    @DeleteMapping("/{id}")
    public String deleteOffer(@PathVariable Long id) {
        service.deleteOffer(id);
        return "Offer deleted successfully";
    }
}
