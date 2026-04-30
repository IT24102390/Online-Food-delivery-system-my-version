package com.dreamora.Controller;

import com.dreamora.model.MenuItem;
import com.dreamora.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CustomerController {

    @Autowired
    private MenuItemService menuItemService;

    private final List<String> categories = Arrays.asList(
            "Rice & Curry", "Kottu", "Burgers", "Fried Rice", "Indian", "Beverages", "Desserts"
    );

    @GetMapping("/customer")
    public String viewCustomerPage(
            @RequestParam(required = false, defaultValue = "All") String category,
            @RequestParam(required = false) String keyword,
            Model model) {

        List<MenuItem> allItems = menuItemService.getAllMenuItems();

        List<MenuItem> items = new ArrayList<>(allItems);

        // 1. filter by category
        if (category != null && !category.equalsIgnoreCase("All")) {
            items = items.stream()
                    .filter(item -> item.getCategory() != null &&
                            item.getCategory().trim().equalsIgnoreCase(category.trim()))
                    .collect(Collectors.toList());
        }

        // 2. filter by keyword
        if (keyword != null && !keyword.trim().isEmpty()) {
            String lowerKeyword = keyword.trim().toLowerCase();

            items = items.stream()
                    .filter(item ->
                            item.getName() != null &&
                                    item.getName().toLowerCase().contains(lowerKeyword)
                    )
                    .collect(Collectors.toList());
        }

        // 3. combo meals first
        // FORCE combo meals to top
        List<MenuItem> comboItems = items.stream()
                .filter(MenuItem::isCombo)
                .collect(Collectors.toList());

        List<MenuItem> normalItems = items.stream()
                .filter(item -> !item.isCombo())
                .collect(Collectors.toList());

// optional: sort inside groups (latest first)
        comboItems.sort(Comparator.comparing(MenuItem::getId).reversed());
        normalItems.sort(Comparator.comparing(MenuItem::getId).reversed());

// merge again
        items = new ArrayList<>();
        items.addAll(comboItems);
        items.addAll(normalItems);

        model.addAttribute("items", items);
        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("searchKeyword", keyword);

        return "customer";
    }
}