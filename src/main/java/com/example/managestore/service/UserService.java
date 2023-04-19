package com.example.managestore.service;

import com.example.managestore.entity.Role;
import com.example.managestore.entity.UserCredential;
import com.example.managestore.entity.employee.Employee;
import com.example.managestore.exception.entityException.EntityExistedException;
import com.example.managestore.exception.entityException.EntityNotFoundException;
import com.example.managestore.exception.entityException.RepositoryAccessException;
import com.example.managestore.repository.EmployeeRepository;
import com.example.managestore.repository.RoleRepository;
import com.example.managestore.repository.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public Page<UserCredential> getAll(int page, int size) {
        return userCredentialRepository.findAll(PageRequest.of(page, size));
    }

    public UserCredential insert(UserCredential userCredential) {
        try {
            if (userCredentialRepository.existsByUsername(userCredential.getUsername()))
                throw new EntityExistedException(String.format("User with Username is %s have already been existed", userCredential.getUsername()));
            userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
            UserCredential userCredentialInserted = userCredentialRepository.save(userCredential);
            return userCredentialInserted;
        } catch (DataAccessException e) {
            log.error("Unable save user");
            throw new RepositoryAccessException("Unable save user");
        }
    }

    public UserCredential createCredentialForEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> {
            log.debug(String.format("Not found Employee with Id = %f", employeeId));
            throw new EntityNotFoundException(String.format("Not found Employee with Id = %f", employeeId));
        });
        String username = employee.getUsernameForEmployee();
        Integer cnt = userCredentialRepository.countUserContainUsername(username);
        if(cnt!=0)
            username = username.concat(String.valueOf(cnt));
        UserCredential userCredential = new UserCredential();
        userCredential.setUsername(username);
        userCredential.setPassword(passwordEncoder.encode("123456789"));
        try {
            return userCredentialRepository.save(userCredential);
        }catch (DataAccessException e){
            log.error("Unable save user");
            throw new RepositoryAccessException("Unable save user");
        }
    }

    public void deleteUser(Long userId){
        if(!userCredentialRepository.existsById(userId))
            throw new EntityNotFoundException(String.format("User not found with Id=%f",userId));
        userCredentialRepository.deleteById(userId);
    }
    public Role insertRole(Role role) {
        try {
            if (roleRepository.existsByName(role.getName())) {
                throw new EntityExistedException(String.format("Role with Name is %s have already been existed", role.getName()));
            } else {
                Role roleInserted = roleRepository.save(role);
                return roleInserted;
            }
        } catch (DataAccessException e) {
            log.error("Unable save role");
            throw new RepositoryAccessException("Unable save role");
        }
    }

    public UserCredential setRoleForUser(Long userId, Long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException(String.format("Role with Id = %f not found", roleId));
                });
        UserCredential userCredential = userCredentialRepository.findById(userId)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException(String.format("User with Id = %f not found", userId));
                });
        Set<Role> roles = userCredential.getRoles();
        roles.add(role);
        userCredential.setRoles(roles);
        try {
            UserCredential userCredentialUpdated = userCredentialRepository.save(userCredential);
            return userCredentialUpdated;
        } catch (DataAccessException e) {
            throw new RepositoryAccessException("Unable save role");
        }
    }

    public UserCredential cancelRoleOfUser(Long userId, Long roleId) {
        UserCredential userCredential = userCredentialRepository.findById(userId)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException(String.format("User with Id = %f not found", userId));
                });
        Set<Role> roles = userCredential.getRoles().stream().filter(x -> !x.getId().equals(roleId)).collect(Collectors.toSet());
        userCredential.setRoles(roles);
        try {
            UserCredential userCredentialUpdated = userCredentialRepository.save(userCredential);
            return userCredentialUpdated;
        } catch (DataAccessException e) {
            throw new RepositoryAccessException("Unable save role");
        }
    }

    public Set<UserCredential> getAllUserWithRoleId(Long role_id) {
        Role role = roleRepository.findById(role_id)
                .orElseThrow(() -> {
                    throw new RepositoryAccessException("bug");
                });
        return role.getUserCredentials();
    }

}
