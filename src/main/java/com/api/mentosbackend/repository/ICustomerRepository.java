package com.api.mentosbackend.repository;

import com.api.mentosbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ICustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findCustomersByCycle(int cycle);
    List<Customer> findCustomersByPointsGreaterThanEqual(Long points);
    List<Customer> findTop100ByOrderByPointsDesc();
    List<Customer> findCustomersByCustomerType(String customerType);
    Optional<Customer> findCustomerByEmail(String email);
}
