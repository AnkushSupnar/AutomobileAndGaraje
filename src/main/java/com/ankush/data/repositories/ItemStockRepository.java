package com.ankush.data.repositories;

import com.ankush.data.entities.ItemStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemStockRepository extends JpaRepository<ItemStock, Long> {
    ItemStock findByItem_Partno(String partno);
    ItemStock findByItem_Itemname(String itemname);


}