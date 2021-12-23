package com.ankush.data.repositories;

import com.ankush.data.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("select itemname from Item")
    List<String> findAllItemNames();

    @Query("select partno from Item")
    List<String>findAllPartno();

    List<Item>findByPartnoContains(String partno);

    Item findByItemnameAndPartno(String itemname,String partno);




}