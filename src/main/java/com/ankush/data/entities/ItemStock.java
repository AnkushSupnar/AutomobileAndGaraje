package com.ankush.data.entities;

import lombok.*;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;

@Entity
@Table(name="itemstock")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ItemStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "itemid")
    private Item item;

    @Column(name = "quantity")
    private Float quantity;

    @Column(name = "purchaserate")
    private Float purchaserate;

    @Column(name = "sallingrate")
    private Float sallingrate;

}