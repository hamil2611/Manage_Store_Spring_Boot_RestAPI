package com.example.managestore.repository.manageProduct;

import com.example.managestore.entity.order.Customer;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
    @CacheEvict(value = {"findAllCustomer"}, allEntries = true)
    Customer save(Customer customer);

    @Cacheable(value = "findAllCustomer")
    Page<Customer> findAll(Pageable pageable);
}
