package com.ankush.data.service;

import com.ankush.data.entities.PurchaseParty;
import com.ankush.data.repositories.PurchasePartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PurchasePartyService {
    @Autowired
    private PurchasePartyRepository repository;
    public PurchaseParty getPartyById(int id){return repository.getById(id);}
    public List<PurchaseParty>getAllPurchaseParty(){
      return repository.findAll();
    }
    public List<PurchaseParty> getPartyByName(String name){
        return repository.getByName(name);
    }
    public int savePurchaseParty(PurchaseParty party)
    {
        if(party.getId()==null)
        {
            repository.save(party);
            return 1;
        }
        else{
            repository.save(party);
            return 2;
        }
    }
    public List<String>getAllPartyNames(){
        return repository.getAllNames();
    }
}
