package com.api.mentosbackend.service;

import com.api.mentosbackend.entities.Customer;

import java.util.List;
import java.util.Optional;

public interface ICustomerService extends CrudService<Customer, Long> {
    List<Customer> findCustomersByCycle(int cycle) throws Exception;
    List<Customer> findCustomersByPointsGreaterThanEqual(Long points) throws Exception;
    List<Customer> findTop100ByOrderByPointsDesc() throws Exception;
    List<Customer> findCustomersByCustomerType(String customerType) throws Exception;
    //@Query("select b from Booking b where b.checkingDate=:checkingDate and b.checkoutDate=:checkoutDate")

    Optional<Customer> findCustomerByEmail(String email) throws Exception;

}
