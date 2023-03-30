package com.example.managestore.configuration;

import com.example.managestore.entity.employee.Employee;
import com.example.managestore.entity.dto.EmployeeDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        PropertyMap<Employee, EmployeeDto> employeeMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setEmail(source.getEmail());
            }
        };
        modelMapper.addMappings(employeeMap);
        return modelMapper;
    }
}
