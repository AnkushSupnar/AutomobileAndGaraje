package com.ankush.data.repositories;

import com.ankush.data.entities.ItemStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ItemStockRepository extends JpaRepository<ItemStock, Long> {
    List<ItemStock> findByItem_Partno(String partno);

    List<ItemStock> findTopByItem_Itemname(String itemname);
    ItemStock findByItem_ItemnameAndItem_PartnoAndPurchaserate(String itemname,String partno,Float purchaserate);
    ItemStock findByItem_Id(Long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update ItemStock set quantity=quantity-:qty where id=:id")
    void reduceStock(@Param("id") Long id,@Param("qty") Float qty);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update ItemStock set quantity=quantity+:qty where id=:id")
    void addStock(@Param("id") Long id,@Param("qty") Float qty);


    ItemStock findByItem_ItemnameAndItem_Partno(String itemname, String partno);

}