package sk.stuba.fei.uim.oop.assignment3.cart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemWithoutId {
    private Long productId;
    private int amount;

    public CartItemWithoutId(CartItem cartItem) {
        this.productId = cartItem.getProduct().getId();
        this.amount = cartItem.getQuantity();
    }
}
