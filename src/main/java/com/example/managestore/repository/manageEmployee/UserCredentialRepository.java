package com.example.managestore.repository.manageEmployee;

import com.example.managestore.entity.employee.UserCredential;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential, Long> {
    Page<UserCredential> findAll(Pageable pageable);

    @Cacheable(value = "findByIdUserCredential")
    Optional<UserCredential> findByUsername(@Param(value = "username") String username);

    @CacheEvict(value = {"findByIdUserCredential"})
    UserCredential save(UserCredential userCredential);

    boolean existsByUsername(String username);

    boolean existsById(Long id);

    boolean existsByEmployeeId(Long employeeId);

    @Query(value = "select count(u) " +
            "from UserCredential u " +
            "where u.username like CONCAT('%',:username,'%') ")
    Integer countUserContainUsername(@Param(value = "username") String username);
}
