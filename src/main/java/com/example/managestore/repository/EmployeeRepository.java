package com.example.managestore.repository;

import com.example.managestore.entity.dto.ShiftDto;
import com.example.managestore.entity.employee.Employee;
import com.example.managestore.entity.employee.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsById(@Param(value = "employeeId") Long employeeId);

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
            "OR (:enable = FALSE AND e.enable = FALSE AND e.email like CONCAT('%',:email,'%') AND (e.createdDate BETWEEN :startDateCreated AND :endDateCreated))"
    )
    List<Employee> filterEmployee(@Param(value = "email") String email,
                                  @Param(value = "startDateCreated") LocalDateTime startDateCreated,
                                  @Param(value = "endDateCreated") LocalDateTime endDateCreated,
                                  @Param(value = "enable") boolean enable);

}
