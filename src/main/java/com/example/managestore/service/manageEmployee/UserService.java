package com.example.managestore.service.manageEmployee;

import com.example.managestore.domain.Grid;
import com.example.managestore.entity.employee.Role;
import com.example.managestore.entity.employee.UserCredential;
import com.example.managestore.domain.UserDto;
import com.example.managestore.entity.employee.Employee;
import com.example.managestore.utils.Constants;
import com.example.managestore.exception.entityException.*;
import com.example.managestore.repository.manageEmployee.EmployeeRepository;
import com.example.managestore.repository.manageEmployee.RoleRepository;
import com.example.managestore.repository.manageEmployee.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserCredentialRepository userCredentialRepository;
    private final RoleRepository roleRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public void authenticationUser(String username, String password) {
        UserCredential userCredential = userCredentialRepository.findByUsername(username).orElseThrow(() -> {
            throw new AuthenticationUserException();
        });
        if (!passwordEncoder.matches(password, userCredential.getPassword())) {
            throw new AuthenticationUserException();
        }
    }

    public Page<UserCredential> getAll(int page, int size) {
        return userCredentialRepository.findAll(PageRequest.of(page, size));
    }

    public UserCredential insert(UserCredential userCredential) {
        try {
            if (userCredentialRepository.existsByUsername(userCredential.getUsername()))
                throw new EntityExistedException(String.format(Constants.ENTITY_NOT_FOUND));
            userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
            UserCredential userCredentialInserted = userCredentialRepository.save(userCredential);
            return userCredentialInserted;
        } catch (DataAccessException e) {
            log.error(Constants.UNABLE_SAVE_RECORD);
            throw new RepositoryAccessException(Constants.UNABLE_SAVE_RECORD);
        }
    }

    public UserCredential createCredentialForEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> {
            log.error(Constants.EMPLOYEE_NOT_FOUND + employeeId);
            throw new EntityNotFoundException(Constants.EMPLOYEE_NOT_FOUND + employeeId);
        });
        if (!employee.isEnable()) {
            log.error("Employee enable is false");
            throw new EmployeeNoActiveException("Employee has been deactivated or deleted");
        }
        if (userCredentialRepository.existsByEmployeeId(employeeId)) {
            log.error(Constants.USER_EXISTED_FOR_EMPLOYEE + employeeId);
            throw new EmployeeNoActiveException(Constants.USER_EXISTED_FOR_EMPLOYEE + employeeId);
        }
        String username = employee.getUsernameForEmployee();
        Integer cnt = userCredentialRepository.countUserContainUsername(username);
        if (cnt != 0)
            username = username.concat(String.valueOf(cnt));
        UserCredential userCredential = new UserCredential();
        userCredential.setUsername(username);
        userCredential.setPassword(passwordEncoder.encode(username));
        userCredential.setEmployee(employee);
        try {
            return userCredentialRepository.save(userCredential);
        } catch (DataAccessException e) {
            log.error(Constants.UNABLE_SAVE_RECORD);
            throw new RepositoryAccessException(Constants.UNABLE_SAVE_RECORD);
        }
    }

    public void deleteUser(Long userId) {
        if (!userCredentialRepository.existsById(userId)) {
            log.error(Constants.USER_NOT_FOUND + userId);
            throw new EntityNotFoundException(Constants.USER_NOT_FOUND + userId);
        }
        userCredentialRepository.deleteById(userId);
    }

    public Role insertRole(Role role) {
        try {
            if (roleRepository.existsByName(role.getName())) {
                throw new EntityExistedException(Constants.ROLE_NAME_EXISTED + role.getName());
            } else {
                Role roleInserted = roleRepository.save(role);
                return roleInserted;
            }
        } catch (DataAccessException e) {
            log.error(Constants.UNABLE_SAVE_RECORD);
            throw new RepositoryAccessException(Constants.UNABLE_SAVE_RECORD);
        }
    }

    public Page<Role> getAllRole(int page, int size, Grid grid){
        if(StringUtils.isBlank(grid.getGridName()))
            grid.setGridName("id");
        Pageable pageable = PageRequest.of(page,size,grid.getSort().equals("asc")
                                                        ?Sort.by(grid.getGridName()).ascending()
                                                        :Sort.by(grid.getGridName()).descending());
        return roleRepository.findAll(pageable);
    }
    public UserCredential setRoleForUser(Long userId, Long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException(Constants.ROLE_NOT_FOUND + roleId);
                });
        UserCredential userCredential = userCredentialRepository.findById(userId)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException(Constants.USER_NOT_FOUND + userId);
                });
        Set<Role> roles = userCredential.getRoles();
        roles.add(role);
        userCredential.setRoles(roles);
        try {
            UserCredential userCredentialUpdated = userCredentialRepository.save(userCredential);
            return userCredentialUpdated;
        } catch (DataAccessException e) {
            log.error(Constants.UNABLE_SAVE_RECORD);
            throw new RepositoryAccessException(Constants.UNABLE_SAVE_RECORD);
        }
    }

    public UserCredential cancelRoleOfUser(Long userId, Long roleId) {
        UserCredential userCredential = userCredentialRepository.findById(userId)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException(Constants.USER_NOT_FOUND + userId);
                });
        Set<Role> roles = userCredential.getRoles().stream().filter(x -> !x.getId().equals(roleId)).collect(Collectors.toSet());
        userCredential.setRoles(roles);
        try {
            UserCredential userCredentialUpdated = userCredentialRepository.save(userCredential);
            return userCredentialUpdated;
        } catch (DataAccessException e) {
            log.error(Constants.UNABLE_SAVE_RECORD);
            throw new RepositoryAccessException(Constants.UNABLE_SAVE_RECORD);
        }
    }

    @PreAuthorize("hasAnyRole('OWNER')")
    public Page<UserDto> getAllInfoUserOfRoleId(Long roleId, int page, int size) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> {
                    throw new RepositoryAccessException(Constants.ROLE_NOT_FOUND + roleId);
                });
        List<UserDto> userDtos = new ArrayList<>();
        for (UserCredential user : role.getUserCredentials()) {
            UserDto userDto = UserDto.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .fullName(user.getEmployee() == null ? "" : user.getEmployee().getFullName())
                    .email(user.getEmployee() == null ? "" : user.getEmployee().getEmail())
                    .build();
            userDtos.add(userDto);
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<UserDto> pageUser = new PageImpl<>(userDtos, pageable, userDtos.size());
        return pageUser;
    }

}
