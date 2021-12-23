package com.ankush.data.repositories;

import com.ankush.data.entities.ItemStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemStockRepository extends JpaRepository<ItemStock, Long> {
    ItemStock findByItem_Partno(String partno);

    List<ItemStock> findTopByItem_Itemname(String itemname);
    ItemStock findByItem_ItemnameAndItem_PartnoAndPurchaserate(String itemname,String partno,Float purchaserate);
    ItemStock findByItem_Id(Long id);

    @Modifying(clearAutomatically = true)
    @Query("update ItemStock set quantity=quantity-:qty where id=:id")
    void reduceStock(@Param("id") Long id,@Param("qty") Float qty);
}