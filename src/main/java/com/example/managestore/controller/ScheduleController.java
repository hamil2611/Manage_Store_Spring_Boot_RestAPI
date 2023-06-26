package com.example.managestore.controller;

import com.example.managestore.domain.ShiftDto;
import com.example.managestore.service.manageEmployee.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping("/insert")
    public ResponseEntity<ShiftDto> insertShift(@Valid @RequestBody ShiftDto shiftDto){
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.insert(shiftDto));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<ShiftDto>> getAllShift(){
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getAll());
    }
}
