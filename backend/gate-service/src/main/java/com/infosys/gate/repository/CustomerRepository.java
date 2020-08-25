package com.infosys.gate.repository;

import java.util.Optional;

import com.infosys.gate.model.Customer;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findById(Long id);
}
