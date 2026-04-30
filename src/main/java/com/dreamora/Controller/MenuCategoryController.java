package com.dreamora.Controller;

import ch.qos.logback.core.model.Model;
import com.dreamora.model.MenuCategory;
import com.dreamora.service.MenuCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/menu-categories")
@CrossOrigin
public class MenuCategoryController {

    @Autowired
    private MenuCategoryService service;

    // 1️⃣ Get all categories
    @GetMapping
    public List<MenuCategory> getAllCategories() {
        return service.getAllCategories();
    }

    // 2️⃣ Get a single category by ID
    @GetMapping("/{id}")
    public MenuCategory getCategoryById(@PathVariable Long id) {
        return service.getCategoryById(id);
    }

    // 3️⃣ Create a new category
    @PostMapping
    public MenuCategory createCategory(@RequestBody MenuCategory category) {
        return service.createCategory(category);
    }

    // 4️⃣ Update an existing category
    @PutMapping("/{id}")
    public MenuCategory updateCategory(@PathVariable Long id, @RequestBody MenuCategory category) {
        return service.updateCategory(id, category);
    }

    // 5️⃣ Delete a category
    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable Long id) {
        service.deleteCategory(id);
        return "Category deleted successfully";
    }

}
