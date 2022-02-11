package com.ankush.data.service;

import com.ankush.data.entities.Bill;
import com.ankush.data.repositories.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BillService {
    @Autowired
    private BillRepository repository;

    public int save(Bill bill)
    {
        if(bill.getId()==null)
        {
            repository.save(bill);
            return 1;
        }
        else {
            repository.save(bill);
            return 2;
        }
    }
    public List<Bill>getBillByDate(LocalDate date)
    {
        return repository.findByDate(date);
    }
    public List<Bill>getAllBills(){return repository.findAll();}
    public List<Bill>getByCustomerName(String name){return repository.findByCustomer_Customername(name);}
    public Optional<Bill> getByBillno(Long billno)
    {
       if(repository.findById(billno)!=null)
       {
           return repository.findById(billno);
       }
       else return null;
    }
}
