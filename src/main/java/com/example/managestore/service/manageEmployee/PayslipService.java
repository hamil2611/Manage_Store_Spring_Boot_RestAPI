package com.example.managestore.service.manageEmployee;

import com.example.managestore.entity.dto.PayslipDto;
import com.example.managestore.entity.employee.Employee;
import com.example.managestore.entity.employee.Payslip;
import com.example.managestore.entity.employee.Shift;
import com.example.managestore.enums.PayslipStatus;
import com.example.managestore.exception.entityException.EntityNotFoundException;
import com.example.managestore.exception.entityException.RepositoryAccessException;
import com.example.managestore.repository.manageEmployee.EmployeeRepository;
import com.example.managestore.repository.manageEmployee.PayslipRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PayslipService {
    private final PayslipRepository payslipRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public List<PayslipDto> getALl(){
        return payslipRepository.findAll().stream().map(x -> modelMapper.map(x,PayslipDto.class)).collect(Collectors.toList());
    }

    public PayslipDto calcuSalaryForEmployee(Long employeeId, LocalDateTime startTime, LocalDateTime endTime){
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(()->{
           throw new EntityNotFoundException(String.format("Employee not found with id = %f",employeeId));
        });
        Float totalHours = Float.valueOf(0);

        for (Shift shift : employee.getShifts()
                .stream()
                .filter(x -> x.getTimeShift().isAfter(startTime) && x.getTimeShift().isBefore(endTime))
                .collect(Collectors.toList())) {
            totalHours+=shift.getNumberOfHours();
        }
        System.out.println(totalHours);
        System.out.println(employee.getLevelSalary());
        Payslip payslip = new Payslip();
        payslip.setTotalHours(totalHours);
        payslip.setSalary(totalHours*employee.getLevelSalary());
        payslip.setStatus(PayslipStatus.PENDING);
        payslip.setCreatedDate(LocalDateTime.now());
        payslip.setLastUpdated(LocalDateTime.now());
        payslip.setEmployee(employee);
        try{
            Payslip payslipInserted = payslipRepository.save(payslip);
            return modelMapper.map(payslipInserted,PayslipDto.class);
        }catch (DataAccessException e){
            log.error("Unable save payslip");
            throw new RepositoryAccessException("Unable save payslip");
        }
    }
}
