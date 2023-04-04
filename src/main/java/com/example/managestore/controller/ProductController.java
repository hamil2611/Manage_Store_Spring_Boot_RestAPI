package com.example.managestore.controller;

import com.example.managestore.entity.product.clothes.Clothes;
import com.example.managestore.entity.dto.ClothesDto;
import com.example.managestore.service.ClothesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shirt")
public class ProductController {
    private final ClothesService clothesService;

    @PostMapping("/insert")
    public ResponseEntity<Clothes> insertShirt(@Valid @RequestBody ClothesDto clothesDto){
        return ResponseEntity.ok().body(clothesService.insert(clothesDto));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<ClothesDto>> getAllPaging(){
        return ResponseEntity.ok().body(clothesService.getAll());
    }

    @PutMapping("/update")
    public ResponseEntity<ClothesDto> updateShirt(@Valid @RequestBody ClothesDto clothesDto){
        return ResponseEntity.ok().body(clothesService.update(clothesDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteShirt(@PathVariable Long id){
        clothesService.delete(id);
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }
}
