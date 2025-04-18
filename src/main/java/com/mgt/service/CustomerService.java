package com.mgt.Service;

import com.mgt.Model.Customer;

import java.util.List;

public interface CustomerService {

    Customer saveCustomer(Customer customer);
    List<Customer> getAllCustomers();
    Customer getCustomerById(int id);
   // void deleteCustomer(int id);
}
