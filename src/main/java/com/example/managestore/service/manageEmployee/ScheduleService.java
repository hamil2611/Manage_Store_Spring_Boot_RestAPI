package com.example.managestore.service.manageEmployee;

import com.example.managestore.domain.ShiftDto;
import com.example.managestore.entity.employee.Employee;
import com.example.managestore.entity.employee.Shift;
import com.example.managestore.utils.Constants;
import com.example.managestore.exception.entityException.EntityNotFoundException;
import com.example.managestore.exception.entityException.RepositoryAccessException;
import com.example.managestore.repository.manageEmployee.EmployeeRepository;
import com.example.managestore.repository.manageEmployee.ShiftRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleService {
    private final ShiftRepository shiftRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public ShiftDto insert(ShiftDto shiftDto) {
//        if (shiftRepository.existsByTimeShift(shiftDto.convertTime())) {
//            throw new EntityExistedException(Constants.SHIFT_EXISTED_IN_TIME + shiftDto.getTimeShift().toString());
//        }
        try {
            Shift shift = shiftDto.toEntity();
            shift.setCreatedDate(LocalDateTime.now());
            shift.setLastUpdated(LocalDateTime.now());
            return shiftRepository.save(shift).toDto();
        } catch (DataAccessException e) {
            log.error(Constants.UNABLE_SAVE_RECORD);
            throw new RepositoryAccessException(Constants.UNABLE_SAVE_RECORD);
        }
    }

    public List<ShiftDto> getAll() {
        return shiftRepository.findAll().stream().map(x -> x.toDto()).collect(Collectors.toList());
    }

    public List<ShiftDto> chooseShift(Long employeeId, Long shiftId) {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> {
            throw new EntityNotFoundException(Constants.EMPLOYEE_NOT_FOUND + employeeId);
        });

        Shift shift = shiftRepository.findById(shiftId).orElseThrow(() -> {
            throw new EntityNotFoundException(Constants.EMPLOYEE_NOT_FOUND + employeeId);
        });

        Set<Shift> shifts = employee.getShifts();
        shifts.add(shift);
        employee.setShifts(shifts);
        try {
            Employee employeeInserted = employeeRepository.save(employee);
            return employeeInserted.getShifts()
                    .stream()
                    .map(x -> x.toDto())
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new RepositoryAccessException(Constants.UNABLE_SET_SHIFT_FOR_EMPLOYEE);
        }

    }

    public List<ShiftDto> cancelShift(Long employeeId, Long shiftId) {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> {
            throw new EntityNotFoundException(Constants.EMPLOYEE_NOT_FOUND + employeeId);
        });
        Set<Shift> shifts = employee.getShifts()
                .stream()
                .filter(x -> !x.getId().equals(shiftId))
                .collect(Collectors.toSet());
        employee.setShifts(shifts);
        try {
            employeeRepository.save(employee);
        } catch (DataAccessException e) {
            throw new RepositoryAccessException(Constants.UNABLE_SET_SHIFT_FOR_EMPLOYEE);
        }
        return shifts.stream().map(x -> x.toDto()).collect(Collectors.toList());

    }

    public List<ShiftDto> getAllShiftOfEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> {
            throw new EntityNotFoundException(Constants.EMPLOYEE_NOT_FOUND + employeeId);
        });
        return employee.getShifts()
                .stream()
                .map(x -> x.toDto())
                .collect(Collectors.toList());
    }

    public List<ShiftDto> getShiftForTime(Long employeeId, LocalDateTime startTime, LocalDateTime endTime, boolean isInTime) {
        return employeeRepository.getShiftForEmployeeInTime(startTime, endTime, employeeId, isInTime)
                .stream()
                .map(x -> x.toDto())
                .collect(Collectors.toList());
    }
}
