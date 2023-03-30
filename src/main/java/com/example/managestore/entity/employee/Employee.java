package com.example.managestore.entity.employee;

import com.example.managestore.entity.UserCredential;
import com.example.managestore.entity.bases.BaseEntity;
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
public class Employee extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "date_of_birth")
    private LocalDateTime dateOfBirth;
    @Column(name = "enable")
    private boolean enable;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private UserCredential userCredential;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "employee_shift",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "shift_id")
    )
    private Set<Shift> shifts;
}
