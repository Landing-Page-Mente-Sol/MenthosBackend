package com.api.mentosbackend.service.impl;

import com.api.mentosbackend.entities.Customer;
import com.api.mentosbackend.repository.ICustomerRepository;
import com.api.mentosbackend.service.ICustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CustomerServiceImpl extends CrudServiceImpl<Customer, Long> implements ICustomerService {
    private final ICustomerRepository customerRepository;

    public CustomerServiceImpl(ICustomerRepository customerRepository) {
        super(customerRepository);
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> findCustomersByCycle(int cycle) throws Exception {
        return this.customerRepository.findCustomersByCycle(cycle);
    }

    @Override
    public List<Customer> findCustomersByPointsGreaterThanEqual(Long points) throws Exception {
        return this.customerRepository.findCustomersByPointsGreaterThanEqual(points);
    }

    @Override
    public List<Customer> findTop100ByOrderByPointsDesc() throws Exception {
        return this.customerRepository.findTop100ByOrderByPointsDesc();
    }

    @Override
    public List<Customer> findCustomersByCustomerType(String customerType) throws Exception {
        return this.customerRepository.findCustomersByCustomerType(customerType);
    }

    @Override
    public Optional<Customer> findCustomerByEmail(String email) throws Exception {
        return this.customerRepository.findCustomerByEmail(email);
    }
}
