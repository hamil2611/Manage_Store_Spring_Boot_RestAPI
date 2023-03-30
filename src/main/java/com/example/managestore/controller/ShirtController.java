package com.example.managestore.controller;

import com.example.managestore.entity.product.clothes.Shirt;
import com.example.managestore.entity.dto.ShirtDto;
import com.example.managestore.service.ShirtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shirt")
public class ShirtController {
    private final ShirtService shirtService;

    @PostMapping("/insert")
    public ResponseEntity<Shirt> insertShirt(@Valid @RequestBody ShirtDto shirtDto){
        return ResponseEntity.ok().body(shirtService.insert(shirtDto));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<ShirtDto>> getAllPaging(){
        return ResponseEntity.ok().body(shirtService.getAll());
    }

    @PutMapping("/update")
    public ResponseEntity<ShirtDto> updateShirt(@Valid @RequestBody ShirtDto shirtDto){
        return ResponseEntity.ok().body(shirtService.update(shirtDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteShirt(@PathVariable Long id){
        shirtService.delete(id);
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }
}
