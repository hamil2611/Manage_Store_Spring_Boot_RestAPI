package com.example.managestore.controller;

import com.example.managestore.entity.dto.EmployeeDto;
import com.example.managestore.entity.dto.ShiftDto;
import com.example.managestore.service.manageEmployee.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<List<EmployeeDto>> getAllEmployee() {
        return ResponseEntity.ok().body(employeeService.getAll());
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable(value = "employeeId") Long employeeId) {
        return ResponseEntity.ok().body(employeeService.getEmployee(employeeId));
    }

    @PutMapping("/update")
    public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody @Valid EmployeeDto employeeDto) {
        return ResponseEntity.ok().body(employeeService.update(employeeDto));
    }

    @PutMapping("/activate/{employeeId}")
    public ResponseEntity<EmployeeDto> activateEmployee(@PathVariable(value = "employeeId") Long employeeId) {
        return ResponseEntity.ok().body(employeeService.activate(employeeId));
    }

    @PutMapping("/deactivate/{employeeId}")
    public ResponseEntity<EmployeeDto> deactivateEmployee(@PathVariable(value = "employeeId") Long employeeId) {
        return ResponseEntity.ok().body(employeeService.deactivate(employeeId));
    }

    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable(value = "employeeId") Long employeeId) {
        employeeService.delete(employeeId);
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }

    @PostMapping("/choose-shift")
    public ResponseEntity<List<ShiftDto>> chooseShiftForEmployee(@RequestParam(value = "employeeId") Long employeeId,
                                                                 @RequestParam(value = "shiftId") Long shiftId) {
        return ResponseEntity.ok(employeeService.chooseShift(employeeId, shiftId));
    }

    @PostMapping("/cancel-shift")
    public ResponseEntity<List<ShiftDto>> cancelShiftForEmployee(@RequestParam(value = "employeeId") Long employeeId,
                                                                 @RequestParam(value = "shiftId") Long shiftId) {
        return ResponseEntity.ok(employeeService.cancelShift(employeeId, shiftId));
    }

    @GetMapping("/all-shift/{employeeId}")
    public ResponseEntity<List<ShiftDto>> getShiftOfEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(employeeService.getAllShiftOfEmployee(employeeId));
    }

    @GetMapping("/filter-shift")
    public ResponseEntity<List<ShiftDto>> filterShift(@RequestParam(value = "employeeId", required = false) Long employeeId,
                                                      @RequestParam(value = "startTime", required = false) LocalDateTime startTime,
                                                      @RequestParam(value = "endTime", required = false) LocalDateTime endTime,
                                                      @RequestParam(value = "isInTime", required = false) boolean isInTime) {
        return ResponseEntity.ok(employeeService.getShiftForTime(employeeId, startTime, endTime, isInTime));
    }

    //TODO: Error JPQL
    @GetMapping("/filter-employee")
    public ResponseEntity<List<EmployeeDto>> filterEmployeeALL(@RequestParam(value = "email", required = false) String email,
                                                               @RequestParam(value = "startDateCreated", required = false) LocalDateTime startDateCreated,
                                                               @RequestParam(value = "endDateCreated", required = false) LocalDateTime endDateCreated,
                                                               @RequestParam(value = "enable", required = false) String enable) {
        System.out.println("CALLED API");
        return ResponseEntity.ok(employeeService.filterEmployee(email, startDateCreated, endDateCreated, enable));
    }
}
