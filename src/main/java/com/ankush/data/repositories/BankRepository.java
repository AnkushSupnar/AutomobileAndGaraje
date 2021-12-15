package com.ankush.data.repositories;

import com.ankush.data.entities.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankRepository extends JpaRepository<Bank, Integer> {

    @Query("select name from Bank")
    List<String>getAllBankNames();

    Bank getByName(@Param("name") String name);
}