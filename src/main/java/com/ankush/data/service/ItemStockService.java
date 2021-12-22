package com.ankush.data.service;

import com.ankush.data.entities.ItemStock;
import com.ankush.data.repositories.ItemStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemStockService {
    @Autowired
    private ItemStockRepository repository;

    public ItemStock findByItem_Partno(String partno){
        return repository.findByItem_Partno(partno);
    }

    public ItemStock findByItem_Itemname(String itemname){
        return repository.findByItem_Itemname(itemname);
    }

}
