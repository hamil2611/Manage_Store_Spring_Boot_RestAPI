package com.example.managestore.repository;

import com.example.managestore.entity.UserCredential;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential,Long> {
    Page<UserCredential> findAll(Pageable pageable);
    Optional<UserCredential> findByUsername(String username);
    boolean existsByUsername(String username);

    boolean existsById(Long id);
    @Query(value = "select count(u) " +
            "from UserCredential u " +
            "where u.username like CONCAT('%',:username,'%') ")
    Integer countUserContainUsername(@Param(value = "username") String username);
}
