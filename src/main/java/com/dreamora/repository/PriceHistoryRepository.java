package com.dreamora.repository;

import com.dreamora.model.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceHistoryRepository extends JpaRepository<PriceHistory, Long> {

    List<PriceHistory> findByMenuItemIdOrderByChangedAtDesc(Long menuItemId);

    void deleteByMenuItemId(Long menuItemId);
}
