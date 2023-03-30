package com.example.managestore.service;

import com.example.managestore.entity.dto.ShiftDto;
import com.example.managestore.entity.employee.Employee;
import com.example.managestore.entity.dto.EmployeeDto;
import com.example.managestore.entity.employee.Shift;
import com.example.managestore.exception.entityException.EntityNotFoundException;
import com.example.managestore.exception.repositoryException.RepositoryAccessException;
import com.example.managestore.repository.EmployeeRepository;
import com.example.managestore.repository.ShiftRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ShiftRepository shiftRepository;
    private final ModelMapper modelMapper;

    public EmployeeDto insert(EmployeeDto employeeDto) {
        try {
            Employee employeeInserted = employeeRepository.save(modelMapper.map(employeeDto, Employee.class));
            return modelMapper.map(employeeInserted, EmployeeDto.class);
        } catch (DataAccessException e) {
            throw new RepositoryAccessException("Unable save shirt");
        }
    }

    public List<EmployeeDto> getAll() {
        return employeeRepository.findAll().stream().map(x -> modelMapper.map(x, EmployeeDto.class)).collect(Collectors.toList());
    }

    public void delete(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException("Employee does not exist!");
        });
        employeeRepository.deleteById(id);
    }

    public EmployeeDto update(EmployeeDto employeeDto) {
        Employee employee = modelMapper.map(employeeDto, Employee.class);
        try {
            EmployeeDto employeeUpdated = modelMapper.map(employeeRepository.save(employee), EmployeeDto.class);
            return employeeUpdated;
        } catch (DataAccessException e) {
            throw new RepositoryAccessException("Unable update employee");
        }
    }

    public List<ShiftDto> chooseShift(Long employeeId, Long shiftId){

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> {
            throw new EntityNotFoundException(String.format("Employee with Id=%f does not exist",employeeId));
        });

        Shift shift = shiftRepository.findById(shiftId).orElseThrow(() ->{
           throw new EntityNotFoundException(String.format("Shift with Id=%f does not exist",shiftId)) ;
        });

        Set<Shift> shifts = employee.getShifts();
        shifts.add(shift);
        employee.setShifts(shifts);
        try{
            employeeRepository.save(employee);
        }catch (DataAccessException e){
            throw new RepositoryAccessException("Unable set shift for employee");
        }
        return shifts.stream().map(x-> modelMapper.map(x, ShiftDto.class)).collect(Collectors.toList());
    }

    public List<ShiftDto> getAllShiftOfEmployee(Long employeeId){
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> {
            throw new EntityNotFoundException(String.format("Employee with Id=%f does not exist",employeeId));
        });
        return employee.getShifts().stream().map(x->modelMapper.map(x,ShiftDto.class)).collect(Collectors.toList());
    }
}
