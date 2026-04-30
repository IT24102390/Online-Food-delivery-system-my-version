package com.dreamora.repository;

import com.dreamora.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    // Basic CRUD handled by JpaRepository
    @Modifying
    @Transactional
    void deleteByMenuItemId(Long menuItemId);
}
