package com.example.managestore.controller;

import com.example.managestore.entity.dto.PayslipDto;
import com.example.managestore.service.PayslipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payslip")
public class PayslipController {
    private final PayslipService payslipService;

    @PostMapping("/create")
    public ResponseEntity<PayslipDto> createPayslipForEmployee(@RequestParam(value = "employeeId") Long employeeId,
                                                               @RequestParam(value = "startTime") LocalDateTime startTime,
                                                               @RequestParam(value = "endTime") LocalDateTime endTime) {
        return ResponseEntity.ok(payslipService.calcuSalaryForEmployee(employeeId, startTime, endTime));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<PayslipDto>> getAllPayslip() {
        return ResponseEntity.ok(payslipService.getALl());
    }
}
