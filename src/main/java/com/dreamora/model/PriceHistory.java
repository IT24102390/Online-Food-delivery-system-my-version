package com.dreamora.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "price_history")
public class PriceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "menu_item_id", nullable = false)
    private Long menuItemId;

    @Column(name = "old_price", nullable = false)
    private Double oldPrice;

    @Column(name = "new_price", nullable = false)
    private Double newPrice;

    @Column(name = "old_large_price")
    private Double oldLargePrice;

    @Column(name = "new_large_price")
    private Double newLargePrice;

    @Column(name = "changed_at", nullable = false)
    private LocalDateTime changedAt;

    public Long getId() {
        return id;
    }

    public Long getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(Long menuItemId) {
        this.menuItemId = menuItemId;
    }

    public Double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(Double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public Double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(Double newPrice) {
        this.newPrice = newPrice;
    }

    public Double getOldLargePrice() {
        return oldLargePrice;
    }

    public void setOldLargePrice(Double oldLargePrice) {
        this.oldLargePrice = oldLargePrice;
    }

    public Double getNewLargePrice() {
        return newLargePrice;
    }

    public void setNewLargePrice(Double newLargePrice) {
        this.newLargePrice = newLargePrice;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }

    public void setChangedAt(LocalDateTime changedAt) {
        this.changedAt = changedAt;
    }
}