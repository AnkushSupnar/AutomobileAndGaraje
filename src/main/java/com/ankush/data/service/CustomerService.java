package com.ankush.data.service;
import com.ankush.data.entities.Customer;
import com.ankush.data.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository repository;
    public List<Customer> getAllCustomer(){
        return repository.findAll();
    }
    public Customer getById(Long id)
    {
        return repository.getById(id);
    }
    public int saveCustomer(Customer customer)
    {
        if(customer.getId()==null) {
            repository.save(customer);
            return 1;
        }else
        {
            repository.save(customer);
            return 2;
        }
    }
    public List<String>getAllCustomerNames()
    {
        return repository.getAllCustomerNames();
    }
    public Customer getByCustomerName(String customername)
    {
        return repository.getByCustomername(customername);
    }

}
