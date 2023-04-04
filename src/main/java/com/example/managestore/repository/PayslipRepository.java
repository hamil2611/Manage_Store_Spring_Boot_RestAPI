package com.example.managestore.repository;

import com.example.managestore.entity.employee.Payslip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayslipRepository extends JpaRepository<Payslip,Long> {
}
