package com.example.managestore.controller;

import com.example.managestore.entity.Role;
import com.example.managestore.entity.UserCredential;
import com.example.managestore.exception.entityException.EntityNotFoundException;
import com.example.managestore.service.manageEmployee.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@ControllerAdvice
public class UserController {
    private final UserService userService;

    @PostMapping("/insert")
    public ResponseEntity<UserCredential> insertNewUser(@Valid @RequestBody UserCredential userCredential) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.insert(userCredential));
    }

    @PostMapping("/create/{employeeId}")
    public ResponseEntity<UserCredential> createNewUserForEmployee(@PathVariable(value = "employeeId") Long employeeId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.createCredentialForEmployee(employeeId));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/get-all")
    public ResponseEntity<Page<UserCredential>> getAllUser(@RequestParam(name = "page", defaultValue = "0") int page,
                                                           @RequestParam(name = "size", defaultValue = "5") int size) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll(page, size));
    }

    @PostMapping("/insert-role")
    public ResponseEntity<Role> insertNewRole(@Valid @RequestBody Role role) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.insertRole(role));
    }

    @PostMapping("/set-role")
    public ResponseEntity<UserCredential> setNewRoleForUser(@RequestParam(name = "userId") Long userId,
                                                            @RequestParam(name = "roleId") Long roleId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.setRoleForUser(userId, roleId));
    }

    @PostMapping("/cancel-role")
    public ResponseEntity<UserCredential> cancelRole(@RequestParam(name = "userId") Long userId,
                                                     @RequestParam(name = "roleId") Long roleId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.cancelRoleOfUser(userId, roleId));
    }

    @GetMapping("/role")
    public ResponseEntity<Set<UserCredential>> getAllUserWithRole(@RequestParam(name = "roleId", defaultValue = "1") Long roleId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUserWithRoleId(roleId));
    }
}
