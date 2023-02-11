package com.example.managestore.service;

import com.example.managestore.entity.Role;
import com.example.managestore.entity.UserCredential;
import com.example.managestore.exception.RepositoryAccessException;
import com.example.managestore.repository.RoleRepository;
import com.example.managestore.repository.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserCredentialRepository userCredentialRepository;
    private final RoleRepository roleRepository;

    public Page<UserCredential> getAll(int page, int size){
        return userCredentialRepository.findAll(PageRequest.of(page, size));
    }
    public UserCredential insert(UserCredential userCredential){
        try{
            UserCredential userCredentialInserted = userCredentialRepository.save(userCredential);
            return userCredentialInserted;
        }catch (DataAccessException e){
            logger.debug("Unable save user");
            throw new RepositoryAccessException("Unable save user");
        }
    }

    public Role insertRole(Role role){
        try {
            Role roleInserted = roleRepository.save(role);
            return roleInserted;
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
                    throw new RepositoryAccessException("bud");
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
