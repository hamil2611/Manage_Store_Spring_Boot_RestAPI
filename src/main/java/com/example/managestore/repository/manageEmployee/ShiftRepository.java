package com.example.managestore.repository.manageEmployee;

import com.example.managestore.entity.employee.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ShiftRepository extends JpaRepository<Shift,Long> {
    boolean existsByTimeShift(@Param(value = "timeShift") LocalDateTime timeShift);
}
