package com.example.managestore.controller;

import com.example.managestore.entity.Role;
import com.example.managestore.entity.UserCredential;
import com.example.managestore.exception.entityException.EntityNotFoundException;
import com.example.managestore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@ControllerAdvice
public class UserController {
    private final UserService userService;
    @PostMapping("/insert")
    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<UserCredential> insertNewUser(@Valid @RequestBody UserCredential userCredential){
        return ResponseEntity.ok().body(userService.insert(userCredential));
    }
    @PostMapping("/insert-role")
    public ResponseEntity<Role> insertNewRole(@Valid @RequestBody Role role){
        return ResponseEntity.ok().body(userService.insertRole(role));
    }
    @PostMapping("/set-role")
    public ResponseEntity<UserCredential> setRole(@RequestParam(name = "user_id") Long user_id,
                                               @RequestParam(name = "role_id") Long role_id){
        return ResponseEntity.ok().body(userService.setRoleForUser(user_id,role_id));
    }
    @GetMapping("/get-all")
    public ResponseEntity<Page<UserCredential>> getAllUser(@RequestParam(name = "page", defaultValue = "0") int page,
                                                           @RequestParam(name = "size", defaultValue = "5") int size){
        return ResponseEntity.ok().body(userService.getAll(page,size));
    }
    @GetMapping("/user-with_role-name")
    public ResponseEntity<Set<UserCredential>> getAllUserWithRole(@RequestParam(name = "role_id", defaultValue = "1") Long role_id){
        return ResponseEntity.ok().body(userService.getAllUserWithRoleId(role_id));
    }



}
