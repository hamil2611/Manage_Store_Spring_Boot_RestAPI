package com.example.managestore.controller;

import com.example.managestore.entity.dto.CategoryDto;
import com.example.managestore.service.manageProduct.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/insert")
    public ResponseEntity<CategoryDto> insertCategory(@RequestBody CategoryDto categoryDto) {
        return ResponseEntity.ok().body(categoryService.insert(categoryDto));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<CategoryDto>> getAllCategory() {
        return ResponseEntity.ok().body(categoryService.getAll());
    }

    @PutMapping("/update")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto) {
        return ResponseEntity.ok().body(categoryService.update(categoryDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable(name = "id") Long id) {
        categoryService.delete(id);
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }
}
