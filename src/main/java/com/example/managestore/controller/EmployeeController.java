package com.example.managestore.controller;

import com.example.managestore.domain.Grid;
import com.example.managestore.domain.EmployeeDto;
import com.example.managestore.domain.ShiftDto;
import com.example.managestore.service.manageEmployee.EmployeeService;
import com.example.managestore.service.manageEmployee.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    private final ScheduleService scheduleService;

    @PostMapping("/insert")
    public ResponseEntity<EmployeeDto> insertEmployee( @Valid @RequestBody EmployeeDto employeeDto) {
        return ResponseEntity.ok().body(employeeService.insert(employeeDto));
    }

    @GetMapping("/get-all")
    public ResponseEntity<Page<EmployeeDto>> getAllEmployee(@Valid @RequestBody Grid grid,
                                                            @RequestParam(value = "page" , defaultValue = "0") int page,
                                                            @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.getAll(grid,page,size));
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable(value = "employeeId") Long employeeId) {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.getEmployee(employeeId));
    }

    @PutMapping("/update")
    public ResponseEntity<EmployeeDto> updateEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
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
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.chooseShift(employeeId, shiftId));
    }

    @PostMapping("/cancel-shift")
    public ResponseEntity<List<ShiftDto>> cancelShiftForEmployee(@RequestParam(value = "employeeId") Long employeeId,
                                                                 @RequestParam(value = "shiftId") Long shiftId) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.cancelShift(employeeId, shiftId));
    }

    @GetMapping("/all-shift/{employeeId}")
    public ResponseEntity<List<ShiftDto>> getShiftOfEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getAllShiftOfEmployee(employeeId));
    }

    @GetMapping("/filter-shift")
    public ResponseEntity<List<ShiftDto>> filterShift(@RequestParam(value = "employeeId", required = false) Long employeeId,
                                                      @RequestParam(value = "startTime", required = false) LocalDateTime startTime,
                                                      @RequestParam(value = "endTime", required = false) LocalDateTime endTime,
                                                      @RequestParam(value = "isInTime", required = false) boolean isInTime) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getShiftForTime(employeeId, startTime, endTime, isInTime));
    }

    @GetMapping("/filter-employee")
    public ResponseEntity<Page<EmployeeDto>> filterEmployeeALL(@RequestParam(value = "page", defaultValue = "0") int page,
                                                               @RequestParam(value = "size", defaultValue = "10") int size,
                                                               @RequestParam(value = "email", required = false) String email,
                                                               @RequestParam(value = "startDateCreated", required = false) LocalDateTime startDateCreated,
                                                               @RequestParam(value = "endDateCreated", required = false) LocalDateTime endDateCreated,
                                                               @RequestParam(value = "enable", required = false, defaultValue = "true") String enable,
                                                               @Valid @RequestBody Grid grid) {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.filterEmployee(grid, page, size, email, startDateCreated, endDateCreated, enable));
    }
    @GetMapping("/filter-employee-spec")
    public ResponseEntity<Page<EmployeeDto>> filterEmployeeALLSpec(@RequestParam(value = "page", defaultValue = "0") int page,
                                                               @RequestParam(value = "size", defaultValue = "10") int size,
                                                               @RequestParam(value = "email", required = false) String email,
                                                               @RequestParam(value = "startDateCreated", required = false) LocalDateTime startDateCreated,
                                                               @RequestParam(value = "endDateCreated", required = false) LocalDateTime endDateCreated,
                                                               @RequestParam(value = "enable", required = false, defaultValue = "true") String enable,
                                                               @Valid @RequestBody Grid grid) {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.filterEmployeeSpecification(grid, page, size, email, startDateCreated, endDateCreated, enable));
    }
}
