package com.example.managestore.specifications;

import com.example.managestore.entity.employee.Employee;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SpecificationEmployee {
    public Specification<Employee> filterEmployee(String name,String email, boolean enable ){
        return (root, query, criteriaBuilder) -> {
          List<Predicate> predicates = new ArrayList<>();
          if(StringUtils.isBlank(name))
              predicates.add(criteriaBuilder.like(root.get("firstName"),"%" + name +"%" ));
          return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
