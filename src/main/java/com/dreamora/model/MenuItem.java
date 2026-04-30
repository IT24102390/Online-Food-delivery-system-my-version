package com.dreamora.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;




@Entity
@Table(name = "menu_item")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive")
    private Double price;

    @Transient
    private MultipartFile imageFile;

    private String imageUrl;

    @NotBlank(message = "Category is required")
    private String category;

    private boolean available = true;

    @Column(length = 1000)
    private String tag;

    private String size;

    private Double largePrice;

    @Column(length = 1000)
    private String comboItems;

    @Column(name = "is_combo")
    private boolean combo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name != null ? name.trim() : null;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category != null ? category.trim() : null;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = (tag != null && !tag.trim().isEmpty()) ? tag.trim() : null;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = (size != null && !size.trim().isEmpty()) ? size.trim() : null;
    }

    public Double getLargePrice() {
        return largePrice;
    }

    public void setLargePrice(Double largePrice) {
        this.largePrice = largePrice;
    }

    public boolean isCombo() {
        return combo;
    }

    public void setCombo(boolean combo) {
        this.combo = combo;
    }

    public String getComboItems() {
        return comboItems;
    }

    public void setComboItems(String comboItems) {
        this.comboItems = (comboItems != null && !comboItems.trim().isEmpty()) ? comboItems.trim() : null;
    }
    @Column(name = "display_order")
    private Integer displayOrder;

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                ", category='" + category + '\'' +
                ", available=" + available +
                ", tag='" + tag + '\'' +
                ", combo=" + combo +
                ", comboItems='" + comboItems + '\'' +
                '}';
    }
}