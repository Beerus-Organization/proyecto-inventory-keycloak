package io.github.emfsilva.inventory.controller;

import io.github.emfsilva.inventory.model.Category;
import io.github.emfsilva.inventory.response.CategoryResponseRest;
import io.github.emfsilva.inventory.services.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/v1/category")
public class CategoryRestController {

    private final ICategoryService categoryService;

    @Autowired
    public CategoryRestController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public ResponseEntity<CategoryResponseRest> searchCategories() {
        return categoryService.search();
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryResponseRest> searchById(@PathVariable Long id) {
        return categoryService.searchById(id);
    }

    @PostMapping("categories")
    public ResponseEntity<CategoryResponseRest> save (@RequestBody @Valid Category category) {
        return  categoryService.save(category);
    }

    @PutMapping("categories/{id}")
    public ResponseEntity<CategoryResponseRest> update(@PathVariable Long id, @RequestBody @Valid Category category) {
        return categoryService.update(category, id);
    }

    @DeleteMapping("categories/{id}")
    public ResponseEntity<CategoryResponseRest> delete(@PathVariable Long id) {
        return categoryService.delete(id);
    }
}
