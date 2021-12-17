package com.ankush.data.service;

import com.ankush.data.entities.Item;
import com.ankush.data.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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


}
