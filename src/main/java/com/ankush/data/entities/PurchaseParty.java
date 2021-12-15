package com.ankush.data.entities;

import lombok.*;

import javax.persistence.*;

@Table(name = "purchaseparty")
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseParty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "pan")
    private String pan;

    @Column(name = "gst")
    private String gst;

    @Column(name = "contact")
    private String contact;

}