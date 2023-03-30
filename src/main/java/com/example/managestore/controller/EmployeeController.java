package com.example.managestore.controller;

import com.example.managestore.entity.dto.EmployeeDto;
import com.example.managestore.entity.dto.ShiftDto;
import com.example.managestore.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping("/insert")
    public ResponseEntity<EmployeeDto> insertEmployee(@RequestBody @Valid EmployeeDto employeeDto){
        return ResponseEntity.ok().body(employeeService.insert(employeeDto));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<EmployeeDto>> getAllEmployee(){
        return ResponseEntity.ok().body(employeeService.getAll());
    }

    @PutMapping("/update")
    public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody @Valid EmployeeDto employeeDto){
        return ResponseEntity.ok().body(employeeService.update(employeeDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id){
        employeeService.delete(id);
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }

    @PostMapping("/choose-shift")
    public ResponseEntity<List<ShiftDto>> chooseShiftForEmployee(@RequestParam(value = "employeeId") Long employeeId,
                                                                 @RequestParam(value = "shiftId") Long shiftId){
        System.out.println("CALL API CHOOSE SHIFT");
        return ResponseEntity.ok(employeeService.chooseShift(employeeId,shiftId));
    }

    @GetMapping("/all-shift/{employeeId}")
    public ResponseEntity<List<ShiftDto>> getShiftOfEmployee(@PathVariable Long employeeId){
        return ResponseEntity.ok(employeeService.getAllShiftOfEmployee(employeeId));
    }
}
