package com.example.managestore.repository.manageEmployee;

import com.example.managestore.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    boolean existsByName(@Param(value = "name") String name);
}
