package com.example.managestore.repository;

import com.example.managestore.entity.employee.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface ShiftRepository extends JpaRepository<Shift,Long> {
    boolean existsByTimeShift(@Param(value = "timeShift") LocalDateTime timeShift);
}
