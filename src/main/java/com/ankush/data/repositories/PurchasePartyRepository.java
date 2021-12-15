package com.ankush.data.repositories;

import com.ankush.data.entities.PurchaseParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchasePartyRepository extends JpaRepository<PurchaseParty, Integer> {

    List<PurchaseParty> getByName(String name);

    @Query("select name from PurchaseParty")
    List<String>getAllNames();
}