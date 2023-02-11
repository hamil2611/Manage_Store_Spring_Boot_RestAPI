package com.example.managestore.repository;

import com.example.managestore.entity.UserCredential;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential,Long> {
    Page<UserCredential> findAll(Pageable pageable);
}
