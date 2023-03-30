package com.example.managestore.service;

import com.example.managestore.entity.Role;
import com.example.managestore.entity.UserCredential;
import com.example.managestore.exception.entityException.EntityExistedException;
import com.example.managestore.exception.repositoryException.RepositoryAccessException;
import com.example.managestore.repository.RoleRepository;
import com.example.managestore.repository.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserCredentialRepository userCredentialRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public Page<UserCredential> getAll(int page, int size){
        return userCredentialRepository.findAll(PageRequest.of(page, size));
    }
    public UserCredential insert(UserCredential userCredential){
        try{
            if(userCredentialRepository.findByUsername(userCredential.getUsername()).isPresent()){
                throw new EntityExistedException(String.format("User with Username is %s have already been existed", userCredential.getUsername()));
            }else {
                userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
                UserCredential userCredentialInserted = userCredentialRepository.save(userCredential);
                return userCredentialInserted;
            }
        }catch (DataAccessException e){
            logger.debug("Unable save user");
            throw new RepositoryAccessException("Unable save user");
        }
    }

    public Role insertRole(Role role){
        try {
            if(roleRepository.existsByName(role.getName())){
                throw new EntityExistedException(String.format("Role with Name is %s have already been existed",role.getName()));
            }else{
                Role roleInserted = roleRepository.save(role);
                return roleInserted;
            }
        }catch (DataAccessException e){
            logger.debug("Unable save role");
            throw new RepositoryAccessException("Unable save role");
        }
    }

    public UserCredential setRoleForUser(Long user_id, Long role_id){
        Role role = roleRepository.findById(role_id)
                .orElseThrow(() ->{
                    throw  new RepositoryAccessException("bug");
                });
        UserCredential userCredential = userCredentialRepository.findById(user_id)
                .orElseThrow(()->{
                    throw new RepositoryAccessException("bug");
                });
        Set<Role> roles = userCredential.getRoles();
        roles.add(role);
        userCredential.setRoles(roles);
        try{
            UserCredential userCredentialUpdated = userCredentialRepository.save(userCredential);
            return userCredentialUpdated;
        }catch (DataAccessException e){
            throw  new RepositoryAccessException("Unable save role");
        }
    }
     public Set<UserCredential> getAllUserWithRoleId(Long role_id){
         Role role = roleRepository.findById(role_id)
                 .orElseThrow(() ->{
                     throw  new RepositoryAccessException("bug");
                 });

        return role.getUserCredentials();
     }

}
