package com.example.managestore.repository.manageEmployee;

import com.example.managestore.entity.employee.Employee;
import com.example.managestore.entity.employee.Shift;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor {


    boolean existsById(@Param(value = "employeeId") Long employeeId);

    @Cacheable(value = "findAllEmployee")
    Page<Employee> findAll(Pageable pageable);

    @CacheEvict(value = {"findAllEmployee"}, allEntries = true)
    Employee save(Employee employee);

    @Query(value = "SELECT s " +
            "FROM Employee e " +
            "INNER JOIN e.shifts s " +
            "WHERE (:isInTime=true AND e.id= :employeeId AND s.timeShift BETWEEN :startTime AND :endTime) " +
            "OR (:isInTime=false AND e.id= :employeeId)")
    Set<Shift> getShiftForEmployeeInTime(@Param(value = "startTime") LocalDateTime startTime,
                                         @Param(value = "endTime") LocalDateTime endTime,
                                         @Param(value = "employeeId") Long employeeId,
                                         @Param(value = "isInTime") boolean isInTime);

    @Query(value = "SELECT e " +
            "FROM Employee e " +
            "WHERE (:enable IS NULL AND e.email like CONCAT('%',:email,'%') AND (e.createdDate BETWEEN :startDateCreated AND :endDateCreated)) " +
            "OR (:enable = TRUE AND e.enable = TRUE AND e.email like CONCAT('%',:email,'%') AND (e.createdDate BETWEEN :startDateCreated AND :endDateCreated)) " +
            "OR (:enable = FALSE AND e.enable = FALSE AND e.email like CONCAT('%',:email,'%') AND (e.createdDate BETWEEN :startDateCreated AND :endDateCreated)) "
    )
    Page<Employee> filterEmployee(@Param(value = "email") String email,
                                  @Param(value = "startDateCreated") LocalDateTime startDateCreated,
                                  @Param(value = "endDateCreated") LocalDateTime endDateCreated,
                                  @Param(value = "enable") boolean enable,
                                  Pageable pageable);

}
