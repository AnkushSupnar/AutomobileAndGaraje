package com.ankush.data.repositories;

import com.ankush.data.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("select customername from Customer")
    List<String>getAllCustomerNames();

    Customer getByCustomername(String customername);

}