package com.example.managestore.entity.employee;

import com.example.managestore.entity.UserCredential;
import com.example.managestore.entity.bases.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "employee")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "date_of_birth")
    private LocalDateTime dateOfBirth;
    @Column(name = "level_salary")
    private Float levelSalary;
    @Column(name = "enable")
    private boolean enable;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private UserCredential userCredential;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private Set<Payslip> payslips;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "employee_shift",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "shift_id")
    )
    @JsonIgnore
    private Set<Shift> shifts;

    public String getFullName() {
        return this.lastName + " " + this.firstName;
    }

    public String getUsernameForEmployee() {
        return this.firstName.toLowerCase().concat(".").concat(this.lastName.toLowerCase().replaceAll(" ", ""));
    }
}
