package sk.stuba.fei.uim.oop.assignment3.cart;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class CartEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @MapsId
    private Cart cart;

    @Embedded
    private EmbeddedProduct product;

    private int quantity;

    public CartEntry(Cart cart, EmbeddedProduct product, int quantity) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }
}

