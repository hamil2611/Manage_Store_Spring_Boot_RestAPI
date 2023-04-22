package com.example.managestore.controller;

import com.example.managestore.entity.dto.ShiftDto;
import com.example.managestore.service.manageEmployee.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping("/insert")
    public ResponseEntity<ShiftDto> insertShift(@RequestBody ShiftDto shiftDto){
        return ResponseEntity.ok(scheduleService.insert(shiftDto));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<ShiftDto>> getAllShift(){
        return ResponseEntity.ok(scheduleService.getAll());
    }
}
