package com.dreamora.Controller;

import com.dreamora.model.MenuItem;
import com.dreamora.service.MenuItemService;
import com.dreamora.service.PriceHistoryService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private PriceHistoryService priceHistoryService;

    private final List<String> categories = Arrays.asList(
            "Rice & Curry", "Kottu", "Burgers",
            "Fried Rice", "Indian", "Beverages", "Desserts"
    );

    // Load Admin Page
    @GetMapping("")
    public String adminHome(Model model) {
        model.addAttribute("items", menuItemService.getAllMenuItems());
        model.addAttribute("item", new MenuItem());
        model.addAttribute("categories", categories);
        return "admin";
    }

    // Save or Update Item
    @PostMapping("/save")
    public String saveItem(@Valid @ModelAttribute("item") MenuItem item,
                           BindingResult result,
                           @RequestParam(value = "imageFile", required = false) MultipartFile file,
                           @RequestParam(value = "tag", required = false) String tag,
                           Model model) {

        // Handle tag
        if (tag != null && !tag.trim().isEmpty()) {
            item.setTag(tag.trim());
        } else {
            item.setTag(null);
        }

        // If not combo, remove combo items
        if (!item.isCombo()) {
            item.setComboItems(null);
        }

        // Remove large price for certain categories
        if (isLargePriceNotNeeded(item.getCategory())) {
            item.setLargePrice(null);
            item.setSize(null);
        }

        // Validation errors
        if (result.hasErrors()) {
            model.addAttribute("items", menuItemService.getAllMenuItems());
            model.addAttribute("categories", categories);
            return "admin";
        }

        // Image required for new item
        if (item.getId() == null && (file == null || file.isEmpty())) {
            model.addAttribute("error", "Image is required!");
            model.addAttribute("items", menuItemService.getAllMenuItems());
            model.addAttribute("categories", categories);
            return "admin";
        }

        try {
            // Upload image
            if (file != null && !file.isEmpty()) {
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                String uploadDir = "C:/menu-uploads/";

                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                file.transferTo(uploadPath.resolve(fileName));
                item.setImageUrl("/uploads/" + fileName);

            } else if (item.getId() != null) {
                // Keep old image
                MenuItem existingItem = menuItemService.getMenuItemById(item.getId());
                if (existingItem != null) {
                    item.setImageUrl(existingItem.getImageUrl());
                }
            }

            // Save or update
            if (item.getId() != null) {
                menuItemService.updateMenuItem(item.getId(), item);
            } else {
                menuItemService.saveItem(item);
            }

        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "Failed to upload image.");
            model.addAttribute("items", menuItemService.getAllMenuItems());
            model.addAttribute("categories", categories);
            return "admin";
        }

        return "redirect:/admin";
    }

    // Edit Item
    @GetMapping("/edit/{id}")
    public String editItem(@PathVariable Long id, Model model) {
        MenuItem existingItem = menuItemService.getMenuItemById(id);

        model.addAttribute("item", existingItem);
        model.addAttribute("items", menuItemService.getAllMenuItems());
        model.addAttribute("categories", categories);

        return "admin";
    }

    // Delete Item
    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable Long id) {
        menuItemService.deleteMenuItem(id);
        return "redirect:/admin";
    }

    // Toggle Availability
    @GetMapping("/toggle-availability/{id}")
    public String toggleAvailability(@PathVariable Long id) {
        menuItemService.toggleAvailability(id);
        return "redirect:/admin";
    }

    // Price History Page
    @GetMapping("/price-history/{id}")
    public String viewPriceHistory(@PathVariable Long id, Model model) {
        MenuItem item = menuItemService.getMenuItemById(id);

        model.addAttribute("item", item);
        model.addAttribute("historyList",
                priceHistoryService.getHistoryByMenuItemId(id));

        return "price_history";
    }

    // Helper method
    private boolean isLargePriceNotNeeded(String category) {
        if (category == null) return false;

        String c = category.trim().toLowerCase();
        return c.equals("drinks")
                || c.equals("beverages")
                || c.equals("beverage")
                || c.equals("burgers");
    }
    @GetMapping("/analytics")
    public String viewAnalytics(Model model) {
        model.addAttribute("historyList", priceHistoryService.getAllHistory());
        return "analytics";
    }
}
