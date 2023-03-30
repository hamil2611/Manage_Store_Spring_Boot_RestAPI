package com.example.managestore.repository;

import com.example.managestore.entity.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    boolean existsById(@Param(value = "employeeId") Long employeeId);
}
