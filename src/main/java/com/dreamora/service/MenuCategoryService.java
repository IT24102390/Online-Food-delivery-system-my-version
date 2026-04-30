package com.dreamora.service;

import com.dreamora.model.MenuCategory;
import com.dreamora.repository.MenuCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuCategoryService {

    @Autowired
    private MenuCategoryRepository repository;

    // Get all categories
    public List<MenuCategory> getAllCategories() {
        return repository.findAll();
    }

    // Get category by ID
    public MenuCategory getCategoryById(Long id) {
        Optional<MenuCategory> category = repository.findById(id);
        return category.orElse(null); // return null if not found
    }

    // Create new category
    public MenuCategory createCategory(MenuCategory category) {
        return repository.save(category);
    }

    // Update category
    public MenuCategory updateCategory(Long id, MenuCategory category) {
        Optional<MenuCategory> existingCategory = repository.findById(id);
        if (existingCategory.isPresent()) {
            MenuCategory cat = existingCategory.get();
            cat.setName(category.getName());
            return repository.save(cat);
        } else {
            return null;
        }
    }

    // Delete category
    public void deleteCategory(Long id) {
        repository.deleteById(id);
    }
}
