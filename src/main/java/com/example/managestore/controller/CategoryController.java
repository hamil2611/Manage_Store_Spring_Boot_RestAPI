package com.example.managestore.controller;

import com.example.managestore.domain.CategoryDto;
import com.example.managestore.service.manageProduct.CategoryService;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Schema
@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/insert")
    public ResponseEntity<CategoryDto> insertCategory(@Valid @RequestBody CategoryDto categoryDto) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.insert(categoryDto));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<CategoryDto>> getAllCategory() {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getAll());
    }

    @PutMapping("/update")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.update(categoryDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable(name = "id") Long id) {
        categoryService.delete(id);
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }
}
