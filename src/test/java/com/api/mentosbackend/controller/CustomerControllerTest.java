package com.api.mentosbackend.controller;

import com.api.mentosbackend.entities.Customer;
import com.api.mentosbackend.service.impl.CustomerServiceImpl;
import com.api.mentosbackend.util.UtilFunctions;
import com.api.mentosbackend.util.CustomerGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CustomerController.class)
@ActiveProfiles("test")
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CustomerServiceImpl customerService;

    private List<Customer> customers;

    private final String basePath = "/api/v1/customers";

    @BeforeEach
    void setup() { this.customers = CustomerGenerator.customers(); }

    @Test
    void findAllUsersTest() throws Exception {
        given(customerService.getAll()).willReturn(customers);
        mockMvc.perform(get(this.basePath)).andExpect(status().isOk());
    }

    @Test
    void findUserByIdTest() throws Exception {
        Long id = 1L;
        Customer customer = CustomerGenerator.customer(id);
        given(customerService.getById(id)).willReturn(Optional.of(customer));

        mockMvc.perform(get(this.basePath+"/{id}", id)).andExpect(status().isOk());
    }

    @Test
    void insertUserTest() throws Exception {
        Customer customer = CustomerGenerator.customer();
        mockMvc.perform(post(this.basePath)
                        .content(UtilFunctions.objectAsJson(customer))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }

    @Test
    void updateUserTest() throws Exception {
        Long id = 1L;
        Customer customer = CustomerGenerator.customer(id);
        given(customerService.getById(id)).willReturn(Optional.of(customer));

        mockMvc.perform(put(this.basePath + "/{id}", id)
                        .content(UtilFunctions.objectAsJson(customer))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    void deleteUserTest() throws Exception {
        Long id = 1L;
        Customer customer = CustomerGenerator.customer(id);

        given(customerService.getById(id)).willReturn(Optional.of(customer));

        mockMvc.perform(delete(this.basePath+"/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    void findUsersByCycleTest() throws Exception {
        int cycle = 8;
        customers.forEach(customer -> customer.setCycle(cycle));

        given(customerService.findCustomersByCycle(cycle)).willReturn(customers);

        mockMvc.perform(get(this.basePath + "/search/cycle/{cycle}", cycle))
                .andExpect(status().isOk());
    }

    @Test
    void findUsersByPointsGreaterThanEqualTest() throws Exception {
        Long points = 1500L;
        customers.forEach(customer -> customer.setPoints(points));

        given(customerService.findCustomersByPointsGreaterThanEqual(points)).willReturn(customers);

        mockMvc.perform(get(this.basePath + "/search/points/gte/{points}", points))
                .andExpect(status().isOk());

    }

    @Test
    void findTop100ByOrderByPointsDescTest() throws Exception {
        given(customerService.findTop100ByOrderByPointsDesc()).willReturn(customers);

        mockMvc.perform(get(this.basePath + "/search/points/top/100/desc"))
                .andExpect(status().isOk());
    }

    @Test
    void findUsersByUserTypeTest() throws Exception {
        String type = "tutor";
        customers.forEach(customer -> customer.setCustomerType(type));

        given(customerService.findCustomersByCustomerType(type)).willReturn(customers);

        mockMvc.perform(get(this.basePath + "/search/type/{customerType}", type))
                .andExpect(status().isOk());
    }

    @Test
    void findUserByEmailTest() throws Exception {
        String email = "example@email.com";
        Customer customer = CustomerGenerator.customer();

        customer.setEmail(email);

        given(customerService.findCustomerByEmail(email)).willReturn(Optional.of(customer));

        mockMvc.perform(get(this.basePath + "/search/email/{email}", email))
                .andExpect(status().isOk());
    }
}
