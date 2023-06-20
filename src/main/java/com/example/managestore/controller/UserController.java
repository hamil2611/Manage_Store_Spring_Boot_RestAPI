package com.example.managestore.controller;

import com.example.managestore.domain.Grid;
import com.example.managestore.entity.employee.Role;
import com.example.managestore.entity.employee.UserCredential;
import com.example.managestore.domain.UserDto;
import com.example.managestore.service.manageEmployee.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/insert")
    public ResponseEntity<UserCredential> insertNewUser(@Valid @RequestBody UserCredential userCredential) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.insert(userCredential));
    }

    @GetMapping()
    public ResponseEntity<Page<UserCredential>> getAllUser(@RequestParam(name = "page", defaultValue = "0") int page,
                                                           @RequestParam(name = "size", defaultValue = "10") int size) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll(page, size));
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

    @PostMapping("/insert-role")
    public ResponseEntity<Role> insertNewRole(@Valid @RequestBody Role role) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.insertRole(role));
    }

    @GetMapping("/role/{id}")

    public ResponseEntity<Page<UserDto>> getAllUserWithRole(@PathVariable(name = "id") Long id,
                                                            @RequestParam(name = "page", defaultValue = "0") int page,
                                                            @RequestParam(name = "size", defaultValue = "10") int size) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllInfoUserOfRoleId(id, page, size));
    }

    @GetMapping("/role")
    public ResponseEntity<Page<Role>> getAllRole(@RequestParam(name = "page", defaultValue = "0") int page,
                                                 @RequestParam(name = "size", defaultValue = "10") int size,
                                                 @RequestBody Grid grid) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllRole(page, size, grid));
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

}
