package com.example.managestore.configuration;

import com.example.managestore.entity.dto.PayslipDto;
import com.example.managestore.entity.employee.Employee;
import com.example.managestore.entity.dto.EmployeeDto;
import com.example.managestore.entity.employee.Payslip;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    //TODO: Review PropertyMap
    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        PropertyMap<Employee, EmployeeDto> employeeMap = new PropertyMap<>() {
            @Override
            protected void configure() {

            }
        };
        PropertyMap<Payslip, PayslipDto> payslipMap = new PropertyMap<Payslip, PayslipDto>() {
            @Override
            protected void configure() {
                map().setNameEmployee(source.getEmployee().getFullName());
            }
        };
        modelMapper.addMappings(employeeMap);
        modelMapper.addMappings(payslipMap);
        return modelMapper;
    }
}
