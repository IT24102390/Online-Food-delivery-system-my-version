package com.dreamora.service;

import com.dreamora.model.MenuItem;
import com.dreamora.repository.MenuItemRepository;
import com.dreamora.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dreamora.model.PriceHistory;
import com.dreamora.repository.PriceHistoryRepository;
import java.time.LocalDateTime;
import java.util.List;


@Service
@Transactional
public class MenuItemService {

    @Autowired
    private PriceHistoryRepository priceHistoryRepository;

    @Autowired
    private MenuItemRepository repository;

    @Autowired
    private OfferRepository offerRepository;

    // ✅ Get all menu items
    public List<MenuItem> getAllMenuItems() {
        return repository.findAllByOrderByDisplayOrderAsc();
    }


    // ✅ Get single menu item by ID
    public MenuItem getMenuItemById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found with id " + id));
    }

    // ✅ Save a new menu item
    public MenuItem saveItem(MenuItem item) {
        return repository.save(item);
    }

    // ✅ Update existing menu item
    public MenuItem updateMenuItem(Long id, MenuItem menuItem) {
        MenuItem existing = getMenuItemById(id);

        boolean priceChanged = existing.getPrice() != null
                && menuItem.getPrice() != null
                && !existing.getPrice().equals(menuItem.getPrice());

        boolean largePriceChanged =
                (existing.getLargePrice() != null || menuItem.getLargePrice() != null) &&
                        (
                                existing.getLargePrice() == null ||
                                        menuItem.getLargePrice() == null ||
                                        !existing.getLargePrice().equals(menuItem.getLargePrice())
                        );

        if (priceChanged || largePriceChanged) {
            PriceHistory history = new PriceHistory();
            history.setMenuItemId(existing.getId());
            history.setOldPrice(existing.getPrice());
            history.setNewPrice(menuItem.getPrice());
            history.setOldLargePrice(existing.getLargePrice());
            history.setNewLargePrice(menuItem.getLargePrice());
            history.setChangedAt(LocalDateTime.now());

            priceHistoryRepository.save(history);
        }

        existing.setName(menuItem.getName());
        existing.setPrice(menuItem.getPrice());
        existing.setLargePrice(menuItem.getLargePrice());
        existing.setImageUrl(menuItem.getImageUrl());
        existing.setAvailable(menuItem.isAvailable());
        existing.setCategory(menuItem.getCategory());
        existing.setTag(menuItem.getTag());
        existing.setSize(menuItem.getSize());
        existing.setCombo(menuItem.isCombo());
        existing.setComboItems(menuItem.getComboItems());

        return repository.save(existing);
    }

    // ✅ Delete menu item (with related offers)
    @Transactional
    public void deleteMenuItem(Long id) {

        // delete price history records first
        priceHistoryRepository.deleteByMenuItemId(id);

        // delete related offers if you use offers
        offerRepository.deleteByMenuItemId(id);

        // delete menu item
        repository.deleteById(id);
    }

    // ✅ Search menu items by keyword
    public List<MenuItem> searchMenuItems(String keyword) {
        return repository.findAllByOrderByDisplayOrderAsc();

    }

    // ✅ Toggle availability
    public MenuItem toggleAvailability(Long id) {
        MenuItem item = getMenuItemById(id);
        item.setAvailable(!item.isAvailable());
        return repository.save(item);
    }
}


