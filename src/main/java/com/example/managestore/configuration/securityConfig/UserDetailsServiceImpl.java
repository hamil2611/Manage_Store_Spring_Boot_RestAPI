package com.example.managestore.configuration.securityConfig;

import com.example.managestore.entity.Role;
import com.example.managestore.entity.UserCredential;
import com.example.managestore.exception.entityException.RepositoryAccessException;
import com.example.managestore.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserCredentialRepository userCredentialRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //TODO: Rework message when username not found
        UserCredential userCredential = userCredentialRepository.findByUsername(username).orElseThrow(()->{
            throw new RepositoryAccessException("");
        });
        Set<GrantedAuthority> roles = new HashSet<>();;
        for(Role role:userCredential.getRoles()){
            roles.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new User(userCredential.getUsername(),userCredential.getPassword(),roles);
    }
}
