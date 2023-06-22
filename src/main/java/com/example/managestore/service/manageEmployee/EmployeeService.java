package com.example.managestore.service.manageEmployee;

import com.example.managestore.domain.Grid;
import com.example.managestore.entity.employee.Employee;
import com.example.managestore.domain.EmployeeDto;
import com.example.managestore.utils.Constants;
import com.example.managestore.exception.entityException.EntityNotFoundException;
import com.example.managestore.exception.entityException.RepositoryAccessException;
import com.example.managestore.repository.manageEmployee.EmployeeRepository;
import com.example.managestore.repository.manageEmployee.ShiftRepository;
import com.example.managestore.specifications.SpecificationEmployee;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ShiftRepository shiftRepository;
    private final ModelMapper modelMapper;


    public EmployeeDto insert(EmployeeDto employeeDto) {
        try {
            employeeDto.setCreatedDate(LocalDateTime.now());
            employeeDto.setLastUpdated(LocalDateTime.now());
            employeeDto.setEnable(false);
            Employee employeeInserted = employeeRepository.save(modelMapper.map(employeeDto, Employee.class));
            return modelMapper.map(employeeInserted, EmployeeDto.class);
        } catch (DataAccessException e) {
            log.error(Constants.UNABLE_SAVE_RECORD);
            throw new RepositoryAccessException(Constants.UNABLE_SAVE_RECORD);
        }
    }

    public EmployeeDto getEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> {
            log.error(Constants.EMPLOYEE_NOT_FOUND + employeeId);
            throw new EntityNotFoundException(String.format(Constants.EMPLOYEE_NOT_FOUND + employeeId));
        });
        return modelMapper.map(employee, EmployeeDto.class);
    }

    public Page<EmployeeDto> filterEmployee(Grid grid, int page, int size, String email, LocalDateTime startDateCreated, LocalDateTime endDateCreated, String enable) {
        if (startDateCreated == null || endDateCreated == null) {
            startDateCreated = LocalDate.now().withDayOfMonth(1).atStartOfDay();
            endDateCreated = startDateCreated.plusMonths(1).minusDays(1);
        }
        if (StringUtils.isBlank(grid.getGridName()))
            grid.setGridName("id");
        Pageable pageable = PageRequest.of(page, size, grid.getSort().equals("asc")
                ? Sort.by(grid.getGridName()).ascending()
                : Sort.by(grid.getGridName()).descending());

        return employeeRepository.filterEmployee(email, startDateCreated, endDateCreated, convertToBoolean(enable), pageable)
                .map(x -> modelMapper.map(x,EmployeeDto.class));
    }

    public Page<EmployeeDto> filterEmployeeSpecification(Grid grid, int page, int size, String email, LocalDateTime startDateCreated, LocalDateTime endDateCreated, String enable){
        Pageable pageable = PageRequest.of(page, size, grid.getSort().equals("asc")
                ? Sort.by(grid.getGridName()).ascending()
                : Sort.by(grid.getGridName()).descending());
        Specification specification = SpecificationEmployee.filterEmployee("",email,convertToBoolean(enable));
        return employeeRepository.findAll(specification,pageable)
                .map(x -> modelMapper.map(x,EmployeeDto.class));
    }
    private boolean convertToBoolean(String enable) {
        if (enable.equals("true"))
            return true;
        else if (enable.equals("false"))
            return false;
        else
            return Boolean.parseBoolean(null);
    }

    @Cacheable(cacheNames = "getAllEmployee")
    public Page<EmployeeDto> getAll(Grid grid, int page, int size) {
        if (StringUtils.isBlank(grid.getGridName()))
            grid.setGridName("id");
        Pageable pageable = PageRequest.of(page, size, grid.getSort().equals("ASC")
                ? Sort.by(grid.getGridName()).ascending()
                : Sort.by(grid.getGridName()).descending());
        return employeeRepository.findAll(pageable).map(new Function<Employee, EmployeeDto>() {
            @Override
            public EmployeeDto apply(Employee employee) {
                return modelMapper.map(employee, EmployeeDto.class);
            }
        });
    }

    @CacheEvict(cacheNames = {"getAllEmployee"})
    public EmployeeDto activate(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> {
            log.error(Constants.EMPLOYEE_NOT_FOUND + employeeId);
            throw new EntityNotFoundException(Constants.EMPLOYEE_NOT_FOUND + employeeId);
        });
        employee.setEnable(true);
        try {
            log.debug("Activating Employee");
            Employee employeeUpdated = employeeRepository.save(employee);
            return modelMapper.map(employeeUpdated, EmployeeDto.class);
        } catch (DataAccessException e) {
            log.error(Constants.UNABLE_SAVE_RECORD);
            throw new RepositoryAccessException(Constants.UNABLE_SAVE_RECORD);
        }
    }

    public EmployeeDto deactivate(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> {
            log.error(Constants.EMPLOYEE_NOT_FOUND + employeeId);
            throw new EntityNotFoundException(Constants.EMPLOYEE_NOT_FOUND + employeeId);
        });
        employee.setEnable(false);
        try {
            log.debug("Activating Employee");
            Employee employeeUpdated = employeeRepository.save(employee);
            return modelMapper.map(employeeUpdated, EmployeeDto.class);
        } catch (DataAccessException e) {
            log.error(Constants.UNABLE_SAVE_RECORD);
            throw new RepositoryAccessException(Constants.UNABLE_SAVE_RECORD);
        }
    }

    public void delete(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> {
            throw new EntityNotFoundException(Constants.EMPLOYEE_NOT_FOUND + employeeId);
        });
        employeeRepository.deleteById(employeeId);
    }

    public EmployeeDto update(EmployeeDto employeeDto) {
        Employee employeeExisted = employeeRepository.findById(employeeDto.getId()).orElseThrow(() -> {
            log.error(Constants.EMPLOYEE_NOT_FOUND + employeeDto.getId());
            throw new EntityNotFoundException(Constants.EMPLOYEE_NOT_FOUND + employeeDto.getId());
        });
        employeeExisted.setEmail(employeeDto.getEmail());
        employeeExisted.setLastName(employeeDto.getLastName());
        employeeExisted.setFirstName(employeeDto.getFirstName());
        employeeExisted.setLevelSalary(employeeDto.getLevelSalary());
        employeeExisted.setDateOfBirth(employeeDto.getDateOfBirth());
        employeeExisted.setLastUpdated(LocalDateTime.now());
        try {
            Employee employeeUpdated = employeeRepository.save(employeeExisted);
            return modelMapper.map(employeeUpdated, EmployeeDto.class);
        } catch (DataAccessException e) {
            log.debug(Constants.UNABLE_SAVE_RECORD);
            throw new RepositoryAccessException(Constants.UNABLE_SAVE_RECORD);
        }
    }


}
