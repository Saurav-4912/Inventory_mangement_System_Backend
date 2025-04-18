package com.mgt.Service;

import com.mgt.Model.Customer;
import com.mgt.Repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepo customerRepo;

    @Override
    public Customer saveCustomer(Customer customer) {
        customer.getCustomerProductList().forEach(product -> product.setCustomer(customer));
        return customerRepo.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    @Override
    public Customer getCustomerById(int id) {
        return customerRepo.findById(id).orElse(null);
    }

    /*
    @Override
    public void deleteCustomer(int id) {
        customerRepo.deleteById(id);
    }
     */
}
