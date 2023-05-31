package com.example.managestore.service.manageEmployee;

import com.example.managestore.entity.Role;
import com.example.managestore.entity.UserCredential;
import com.example.managestore.entity.employee.Employee;
import com.example.managestore.enums.Constants;
import com.example.managestore.exception.entityException.*;
import com.example.managestore.repository.manageEmployee.EmployeeRepository;
import com.example.managestore.repository.manageEmployee.RoleRepository;
import com.example.managestore.repository.manageEmployee.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserCredentialRepository userCredentialRepository;
    private final RoleRepository roleRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

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

    public Set<UserCredential> getAllUserWithRoleId(Long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> {
                    throw new RepositoryAccessException(Constants.ROLE_NOT_FOUND + roleId);
                });
        return role.getUserCredentials();
    }

}
