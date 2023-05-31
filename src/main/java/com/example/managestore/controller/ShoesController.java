package com.example.managestore.controller;

import com.example.managestore.entity.dto.ShoesDto;
import com.example.managestore.entity.dto.ShoesItemDto;
import com.example.managestore.service.manageProduct.ShoesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shoes")
@RequiredArgsConstructor
public class ShoesController {
    private final ShoesService shoesService;
    @PostMapping("/insert")
    public ResponseEntity<ShoesDto> insertClothes(@Valid @RequestBody ShoesDto shoesDto) {
        return ResponseEntity.status(HttpStatus.OK).body(shoesService.insertShoes(shoesDto));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<ShoesDto>> getAllPaging() {
        return ResponseEntity.ok().body(shoesService.getAll());
    }

    @PutMapping("/update")
    public ResponseEntity<ShoesDto> updateClothes(@Valid @RequestBody ShoesDto shoesDto) {
        return ResponseEntity.status(HttpStatus.OK).body(shoesService.updateShoes(shoesDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteClothes(@PathVariable(value = "id") Long id) {
        shoesService.deleteShoes(id);
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }

    @PostMapping("/item/create")
    public ResponseEntity<ShoesItemDto> createClothesItem(@Valid @RequestBody ShoesItemDto shoesItemDto) {
        return ResponseEntity.status(HttpStatus.OK).body(shoesService.createItem(shoesItemDto));
    }

    @PostMapping("/item/update")
    public ResponseEntity<ShoesItemDto> updateClothesItem(@Valid @RequestBody ShoesItemDto shoesItemDto) {
        return ResponseEntity.status(HttpStatus.OK).body(shoesService.updateItem(shoesItemDto));
    }

    @GetMapping("/item")
    public ResponseEntity<List<ShoesItemDto>> getAllClothesItem(){
        return ResponseEntity.status(HttpStatus.OK).body(shoesService.getAllItem());
    }

    @DeleteMapping("/item/delete/{id}")
    public ResponseEntity<Void> deleteClothesItem(@PathVariable(value = "id") Long id){
        shoesService.deleteItem(id);
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }
}
