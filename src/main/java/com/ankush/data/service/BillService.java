package com.ankush.data.service;

import com.ankush.data.entities.Bill;
import com.ankush.data.repositories.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
