package com.ankush.data.entities;

import lombok.*;

import javax.persistence.*;

@Table(name = "bank")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
@ToString
@Builder
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "accountno")
    private String accountno;

    @Column(name = "ifsc")
    private String ifsc;

    @Column(name = "branch")
    private String branch;

    @Column(name = "woner")
    private String woner;

    @Column(name = "balance")
    private Float balance;

}