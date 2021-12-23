package com.ankush.data.service;

import com.ankush.data.entities.PurchaseInvoice;
import com.ankush.data.repositories.PurchaseInvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseInvoiceService {
    @Autowired
    private PurchaseInvoiceRepository repository;
    public int saveInvoice(PurchaseInvoice invoice)
    {
        if(invoice.getId()==null){
            repository.save(invoice);
            return 1;
        }
        else
        {
            repository.save(invoice);
            return 2;
        }
    }
    public Optional<PurchaseInvoice> getInvoiceById(Long id){return repository.findById(id);}
    public List<PurchaseInvoice>getAllInvoice(){return repository.findAll();}
    public List<PurchaseInvoice>getInvoiceByDate(LocalDate date){return repository.findAll();}
    public List<PurchaseInvoice>findByDateBetween(LocalDate start,LocalDate end){return repository.findByDateBetween(start,end);}
}
