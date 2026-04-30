package com.dreamora.service;

import com.dreamora.model.PriceHistory;
import com.dreamora.repository.PriceHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceHistoryService {

    @Autowired
    private PriceHistoryRepository repository;

    public PriceHistory save(PriceHistory history) {
        return repository.save(history);
    }

    public List<PriceHistory> getHistoryByMenuItemId(Long menuItemId) {
        return repository.findByMenuItemIdOrderByChangedAtDesc(menuItemId);
    }
    public List<PriceHistory> getAllHistory() {
        return repository.findAll();
    }
}