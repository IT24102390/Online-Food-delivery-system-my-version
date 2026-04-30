package com.dreamora.Controller;



import com.dreamora.model.MenuItem;
import com.dreamora.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

@RestController
@RequestMapping("/api/menu-items")
@CrossOrigin // allow cross-origin requests
public class MenuItemController {

    @Autowired
    private MenuItemService service;

    // 1️⃣ Get all menu items
    @GetMapping
    public ResponseEntity<List<MenuItem>> getAllMenuItems() {
        List<MenuItem> items = service.getAllMenuItems();
        return ResponseEntity.ok(items);
    }

    // 2️⃣ Get a single menu item by ID
    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> getMenuItemById(@PathVariable Long id) {
        MenuItem item = service.getMenuItemById(id);
        if (item != null) {
            return ResponseEntity.ok(item);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 3️⃣ Create / Save a new menu item
    @PostMapping
    public ResponseEntity<MenuItem> createMenuItem(@RequestBody MenuItem menuItem) {
        MenuItem savedItem = service.saveItem(menuItem);
        return ResponseEntity.ok(savedItem);
    }

    // 4️⃣ Update an existing menu item
    @PutMapping("/{id}")
    public ResponseEntity<MenuItem> updateMenuItem(@PathVariable Long id, @RequestBody MenuItem menuItem) {
        MenuItem updatedItem = service.updateMenuItem(id, menuItem);
        if (updatedItem != null) {
            return ResponseEntity.ok(updatedItem);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 5️⃣ Delete a menu item

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMenuItem(@PathVariable Long id) {
        service.deleteMenuItem(id);  // service method is void
        return ResponseEntity.ok("Menu item deleted successfully");
    }
    @PostMapping("/save")
    public ResponseEntity<MenuItem> save(@RequestBody MenuItem item) {
        MenuItem saved = service.saveItem(item); // ✅ call instance method
        return ResponseEntity.ok(saved);
    }

    // 6️⃣ Toggle availability (available / out of stock)
    @PutMapping("/{id}/availability")
    public ResponseEntity<MenuItem> toggleAvailability(@PathVariable Long id) {
        MenuItem item = service.toggleAvailability(id);
        if (item != null) {
            return ResponseEntity.ok(item);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}