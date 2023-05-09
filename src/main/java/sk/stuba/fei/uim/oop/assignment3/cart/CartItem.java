package sk.stuba.fei.uim.oop.assignment3.cart;


import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.assignment3.product.Product;

import javax.persistence.*;

@Getter
@Setter
@Entity

public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne

    @MapsId
    private Cart cart;

    @Embedded
    private EmbeddedProduct product;


    private int quantity;
}