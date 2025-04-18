package com.mgt.Controller;

import com.mgt.Model.Customer;
import com.mgt.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // allow Angular frontend
public class CustomerController {

    @Autowired
    private CustomerService customerService;


@PostMapping("/customers")
public String createCustomer(@RequestBody Customer customer) {
    customerService.saveCustomer(customer);
    return "Invoice created succsessfully to database";
}

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/customers/{id}")
    public Customer getCustomerById(@PathVariable int id) {
        return customerService.getCustomerById(id);
    }
}
