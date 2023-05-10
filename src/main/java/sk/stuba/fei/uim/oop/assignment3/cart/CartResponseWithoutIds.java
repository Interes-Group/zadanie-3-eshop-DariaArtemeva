package sk.stuba.fei.uim.oop.assignment3.cart;


import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;
@Getter
@Setter
public class CartResponseWithoutIds {
    private List<CartItemWithoutId> shoppingList;
    private boolean payed;

    public CartResponseWithoutIds(Cart cart) {
        this.shoppingList = cart.getShoppingList().stream()
                .map(CartItemWithoutId::new)
                .collect(Collectors.toList());
        this.payed = cart.isPayed();
    }


}

