package com.ankush.data.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "purchaseinvoice")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PurchaseInvoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "invoiceno")
    private String invoiceno;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "partyid")
    private PurchaseParty party;

    @ManyToOne
    @JoinColumn(name = "bankid")
    private Bank bank;

    @Column(name = "nettotal")
    private Float nettotal;

    @Column(name = "transport")
    private Float transport;

    @Column(name = "other")
    private Float other;

    @Column(name = "grandtotal")
    private Float grandtotal;

    @OneToMany(mappedBy = "invoice", orphanRemoval = true)
    private List<PurchaseTransaction> purchaseTransactions = new ArrayList<>();

}