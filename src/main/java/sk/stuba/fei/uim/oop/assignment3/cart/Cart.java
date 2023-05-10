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

    @OneToMany
    private List<CartItem> shoppingList;

    private boolean payed;

    public Cart() {
        this.shoppingList = new ArrayList<>(); // Добавьте эту строку для инициализации списка
        this.payed = false;
    }

    public void addItem(CartItem item) {
        shoppingList.add(item);
    }

    public void removeItem(CartItem item) {
        shoppingList.remove(item);
    }

    public double getTotal() {
        return shoppingList.stream().mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity()).sum();
    }
}
