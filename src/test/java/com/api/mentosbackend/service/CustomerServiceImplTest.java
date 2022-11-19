package com.api.mentosbackend.service;

import com.api.mentosbackend.entities.Customer;
import com.api.mentosbackend.repository.ICustomerRepository;
import com.api.mentosbackend.service.impl.CustomerServiceImpl;
import com.api.mentosbackend.util.CustomerGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

    @Mock
    private ICustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    public void saveTest(){
        Customer customer = CustomerGenerator.customer();
        given(customerRepository.save(customer)).willReturn(customer);

        Customer savedCustomer = null;
        try {
            savedCustomer = customerService.save(customer);
        } catch (Exception e){
            e.printStackTrace();
        }

        assertThat(savedCustomer).isNotNull();
        assertEquals(customer, savedCustomer);

    }

    @Test
    public void deleteTest() throws Exception {
        Long id = 1L;
        customerService.delete(id);
        verify(customerRepository, times(1)).deleteById(id);
    }

    @Test
    public void getAllTest() throws Exception {
        List<Customer> customers = CustomerGenerator.customers();
        given(customerRepository.findAll()).willReturn(customers);
        List<Customer> customersExpected = customerService.getAll();
        assertEquals(customersExpected, customers);
    }

    @Test
    public void getByIdTest() throws Exception {
        Long id = 1L;
        Customer customer = CustomerGenerator.customer(id);
        given(customerRepository.findById(id)).willReturn(Optional.of(customer));

        Optional<Customer> customerExpected = customerService.getById(id);

        assertThat(customerExpected).isNotNull();
        assertEquals(customerExpected, Optional.of(customer));
    }

    @Test
    public void findUserByCycleTest() throws Exception {
        int cycle = 5;
        int size = 10;
        List<Customer> customers = CustomerGenerator.customers(size);
        customers.forEach(customer -> customer.setCycle(cycle));

        given(customerRepository.findCustomersByCycle(cycle)).willReturn(customers);

        List<Customer> customersExpected = customerService.findCustomersByCycle(cycle);

        assertEquals(customersExpected, customers);
    }

    @Test
    public void findUsersByPointsGreaterThanEqualTest() throws Exception {
        Long points = 1500L;
        int size = 10;
        List<Customer> customers = CustomerGenerator.customers(size);
        customers.forEach(u -> u.setPoints(points));

        given(customerRepository.findCustomersByPointsGreaterThanEqual(points)).willReturn(customers);

        List<Customer> customersExpected = customerService.findCustomersByPointsGreaterThanEqual(points);

        assertEquals(customersExpected, customers);
    }

    @Test
    public void findTop100ByOrderByPointsDescTest() throws Exception {
        List<Customer> customers = CustomerGenerator.customers();

        given(customerRepository.findTop100ByOrderByPointsDesc()).willReturn(customers);

        List<Customer> customersExpected = customerService.findTop100ByOrderByPointsDesc();

        assertEquals(customersExpected, customers);
    }

    @Test
    public void findUsersByUserType() throws Exception {
        List<Customer> customers = CustomerGenerator.customers();
        String customerType = "tutor";
        customers.forEach(u -> u.setCustomerType(customerType));

        given(customerRepository.findCustomersByCustomerType(customerType)).willReturn(customers);

        List<Customer> customersExpected = customerService.findCustomersByCustomerType(customerType);

        assertEquals(customersExpected, customers);
    }

    @Test
    public void findUserByEmailTest() throws Exception {
        String email = "example@email.com";

        Customer customer = CustomerGenerator.customer();
        customer.setEmail(email);

        given(customerRepository.findCustomerByEmail(email)).willReturn(Optional.of(customer));

        Optional<Customer> customerExpected = customerService.findCustomerByEmail(email);

        assertThat(customerExpected).isNotNull();

        assertEquals(customerExpected, Optional.of(customer));
    }
}
