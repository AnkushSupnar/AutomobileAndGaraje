package com.ankush.data.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "item")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "itemname")
    private String itemname;

    @Column(name = "partno")
    private String partno;


}