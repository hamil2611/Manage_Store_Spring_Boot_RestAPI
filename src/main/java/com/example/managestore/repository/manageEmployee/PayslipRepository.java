package com.example.managestore.repository.manageEmployee;

import com.example.managestore.entity.employee.Payslip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayslipRepository extends JpaRepository<Payslip,Long> {
}
