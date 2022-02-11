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
        if(repository.findByItem_Partno(partno).size()==0)
            return null;
        else
        return repository.findByItem_Partno(partno).get(0);
    }
    public List<ItemStock> getStockByPartno(String partno)
    {
        if(repository.findByItem_Partno(partno).size()==0)
            return null;
        else
            return repository.findByItem_Partno(partno);
    }

    public ItemStock findByItem_Itemname(String itemname){
        if(repository.findTopByItem_Itemname(itemname).size()==0)
           return null;
        else
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
    public void reduceStock(Long id,float qty)
    {
        repository.reduceStock(id,qty);
    }
    public void addStock(Long id,float qty)
    {
        repository.addStock(id,qty);
    }
    public ItemStock findStockByNameAndPartNoAndPurchaseRate(String itemname,String partno,float rate)
    {
        return repository.findByItem_ItemnameAndItem_PartnoAndPurchaserate(itemname,partno,rate);
    }
    public float getItemStock(String partno)
    {
        final float[] qty = {0.0f};
        repository.findByItem_Partno(partno).forEach(s-> qty[0] +=s.getQuantity());
        return qty[0];
    }
}
