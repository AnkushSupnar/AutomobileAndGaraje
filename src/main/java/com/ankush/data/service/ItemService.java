package com.ankush.data.service;

import com.ankush.data.entities.Item;
import com.ankush.data.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {
    @Autowired
    private ItemRepository repository;
    public List<String>getAllItemNames()
    {
        return repository.findAllItemNames();
    }
    public List<Item>getAllItems()
    {
        return repository.findAll();
    }
    public List<String>getAllPartNo()
    {
        return repository.findAllPartno();
    }
    public Item findByPartno(String partno)
    {
        if(repository.findByPartno(partno).size()==0)
            return null;
        else
        return repository.findByPartno(partno).get(0);
    }

    public List<String>findByPartnoLike(String partno)
    {
        return null;
                //findByPartno(partno).stream().map(Item::getItemname).collect(Collectors.toList());
    }
    public Item findByItemNameAndPartno(String itemname,String partno)
    {
        return repository.findByItemnameAndPartno(itemname,partno);
    }
    public Item saveItem(Item item)
    {
        Item i =findByItemNameAndPartno(item.getItemname(),item.getPartno());
        if(i==null)
        {
             return repository.save(item);
        }
        else
        {
            return i;
        }
    }
    public List<String>findPartNameByPartNo(String partno)
    {
        return repository.findItemnameByPartno(partno);
    }
}
