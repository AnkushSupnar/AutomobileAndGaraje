package com.ankush.data.entities;
import lombok.*;
import javax.persistence.*;
@Entity
@Table(name = "transaction")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "partno")
    private String partno;

    @Column(name = "partname")
    private String partname;

    @Column(name = "quantity")
    private Float quantity;

    @Column(name = "rate")
    private Float rate;

    @Column(name = "amount")
    private Float amount;

    @ManyToOne
    @JoinColumn(name = "bill")
    @ToString.Exclude
    private Bill bill;

}