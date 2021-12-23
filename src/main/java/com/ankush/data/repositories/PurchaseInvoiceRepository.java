package com.ankush.data.repositories;

import com.ankush.data.entities.PurchaseInvoice;
import jdk.jfr.Percentage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseInvoiceRepository extends JpaRepository<PurchaseInvoice, Long> {
    List<PurchaseInvoice> findByOrderByIdAsc();

    List<PurchaseInvoice> findByDate(LocalDate date);

   // @Query("select from PurchaseInvoice where date between start and end")
    List<PurchaseInvoice>findByDateBetween(@Param("start") LocalDate start,@Param("end") LocalDate end);



}