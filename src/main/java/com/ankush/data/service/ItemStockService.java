package com.ankush.data.service;
import com.ankush.data.entities.ItemStock;
import com.ankush.data.repositories.ItemStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemStockService {
    @Autowired
    private ItemStockRepository repository;

    public ItemStock findByItem_Partno(String partno){
        return repository.findByItem_Partno(partno);
    }

    public ItemStock findByItem_Itemname(String itemname){
        return repository.findTopByItem_Itemname(itemname).get(0);
    }

    public void saveItemStock(ItemStock stock)
    {
       ItemStock itemStock =
               repository.findByItem_ItemnameAndItem_PartnoAndPurchaserate(
                       stock.getItem().getItemname(),
                       stock.getItem().getPartno(),
                       stock.getPurchaserate());
       if(itemStock==null)
       {
           repository.save(stock);
       }
       else {
           if(itemStock.getSallingrate().longValue()==stock.getSallingrate().longValue())//found Same Rate
           {
               System.out.println("Found Item Stock= "+itemStock);
               itemStock.setQuantity(itemStock.getQuantity()+stock.getQuantity());
               repository.save(itemStock);
           }
           else{//rate not Same
               repository.save(stock);
           }
       }
    }
    public void reduceStock(ItemStock stock)
    {
//        repository.reduceStock(,
//                stock.getQuantity());
    }
}
