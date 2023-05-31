package com.example.managestore.controller;

import com.example.managestore.domain.Grid;
import com.example.managestore.entity.dto.EmployeeDto;
import com.example.managestore.entity.dto.ShiftDto;
import com.example.managestore.service.manageEmployee.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping("/insert")
    public ResponseEntity<EmployeeDto> insertEmployee(@RequestBody @Valid EmployeeDto employeeDto) {
        return ResponseEntity.ok().body(employeeService.insert(employeeDto));
    }

    @GetMapping("/get-all")
    public ResponseEntity<Page<EmployeeDto>> getAllEmployee(@RequestBody Grid grid,
                                                            @RequestParam(value = "page" , defaultValue = "0") int page,
                                                            @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.getAll(grid,page,size));
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable(value = "employeeId") Long employeeId) {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.getEmployee(employeeId));
    }

    @PutMapping("/update")
    public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody @Valid EmployeeDto employeeDto) {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.update(employeeDto));
    }

    @PutMapping("/activate/{employeeId}")
    public ResponseEntity<EmployeeDto> activateEmployee(@PathVariable(value = "employeeId") Long employeeId) {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.activate(employeeId));
    }

    @PutMapping("/deactivate/{employeeId}")
    public ResponseEntity<EmployeeDto> deactivateEmployee(@PathVariable(value = "employeeId") Long employeeId) {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.deactivate(employeeId));
    }

    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable(value = "employeeId") Long employeeId) {
        employeeService.delete(employeeId);
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }

    @PostMapping("/choose-shift")
    public ResponseEntity<List<ShiftDto>> chooseShiftForEmployee(@RequestParam(value = "employeeId") Long employeeId,
                                                                 @RequestParam(value = "shiftId") Long shiftId) {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.chooseShift(employeeId, shiftId));
    }

    @PostMapping("/cancel-shift")
    public ResponseEntity<List<ShiftDto>> cancelShiftForEmployee(@RequestParam(value = "employeeId") Long employeeId,
                                                                 @RequestParam(value = "shiftId") Long shiftId) {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.cancelShift(employeeId, shiftId));
    }

    @GetMapping("/all-shift/{employeeId}")
    public ResponseEntity<List<ShiftDto>> getShiftOfEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.getAllShiftOfEmployee(employeeId));
    }

    @GetMapping("/filter-shift")
    public ResponseEntity<List<ShiftDto>> filterShift(@RequestParam(value = "employeeId", required = false) Long employeeId,
                                                      @RequestParam(value = "startTime", required = false) LocalDateTime startTime,
                                                      @RequestParam(value = "endTime", required = false) LocalDateTime endTime,
                                                      @RequestParam(value = "isInTime", required = false) boolean isInTime) {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.getShiftForTime(employeeId, startTime, endTime, isInTime));
    }

    @GetMapping("/filter-employee")
    public ResponseEntity<Page<EmployeeDto>> filterEmployeeALL(@RequestParam(value = "page", defaultValue = "0") int page,
                                                               @RequestParam(value = "size", defaultValue = "10") int size,
                                                               @RequestParam(value = "email", required = false) String email,
                                                               @RequestParam(value = "startDateCreated", required = false) LocalDateTime startDateCreated,
                                                               @RequestParam(value = "endDateCreated", required = false) LocalDateTime endDateCreated,
                                                               @RequestParam(value = "enable", required = false, defaultValue = "true") String enable,
                                                               @RequestBody Grid grid) {
        System.out.println("CALLED API");
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.filterEmployee(grid, page, size, email, startDateCreated, endDateCreated, enable));
    }
}
