package sk.stuba.fei.uim.oop.assignment3.cart;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.assignment3.product.Product;

@Getter
@Setter
public class CartItemWithoutId {
    private Long productId;
    private int amount;

    public CartItemWithoutId(CartItem cartItem) {
        Product product = cartItem.getProduct();
        if (product != null) {
            this.productId = product.getId();
        } else {
            this.productId = null;
        }
        this.amount = cartItem.getQuantity();
    }

}
