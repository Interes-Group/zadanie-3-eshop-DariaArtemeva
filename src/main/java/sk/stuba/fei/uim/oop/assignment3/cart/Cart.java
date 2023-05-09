package sk.stuba.fei.uim.oop.assignment3.cart;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private List<CartItem> shoppingList;

    private boolean payed;

    public Cart() {
        this.shoppingList = new ArrayList<>();
        this.payed = false;
    }
}
