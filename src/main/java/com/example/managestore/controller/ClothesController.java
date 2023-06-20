package com.example.managestore.controller;

import com.example.managestore.domain.ClothesItemDto;
import com.example.managestore.domain.ClothesDto;
import com.example.managestore.service.manageProduct.ClothesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clothes")
public class ClothesController {
    private final ClothesService clothesService;

    @PostMapping("/insert")
    public ResponseEntity<ClothesDto> insertClothes(@Valid @RequestBody ClothesDto clothesDto) {
        return ResponseEntity.status(HttpStatus.OK).body(clothesService.insertClothes(clothesDto));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<ClothesDto>> getAllPaging() {
        return ResponseEntity.status(HttpStatus.OK).body(clothesService.getAll());
    }

    @PutMapping("/update")
    public ResponseEntity<ClothesDto> updateClothes(@Valid @RequestBody ClothesDto clothesDto) {
        return ResponseEntity.status(HttpStatus.OK).body(clothesService.updateClothes(clothesDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteClothes(@PathVariable(value = "id") Long id) {
        clothesService.deleteClothes(id);
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }

    @PostMapping("/item/create")
    public ResponseEntity<ClothesItemDto> createClothesItem(@Valid @RequestBody ClothesItemDto clothesItemDto) {
        return ResponseEntity.status(HttpStatus.OK).body(clothesService.createItem(clothesItemDto));
    }

    @PostMapping("/item/update")
    public ResponseEntity<ClothesItemDto> updateClothesItem(@Valid @RequestBody ClothesItemDto clothesItemDto) {
        return ResponseEntity.status(HttpStatus.OK).body(clothesService.updateItem(clothesItemDto));
    }

    @GetMapping("/item")
    public ResponseEntity<List<ClothesItemDto>> getAllClothesItem(){
        return ResponseEntity.status(HttpStatus.OK).body(clothesService.getAllItem());
    }

    @DeleteMapping("/item/delete/{id}")
    public ResponseEntity<Void> deleteClothesItem(@PathVariable(value = "id") Long id){
        clothesService.deleteItem(id);
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }
}
