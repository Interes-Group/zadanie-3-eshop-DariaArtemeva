package sk.stuba.fei.uim.oop.assignment3.cart;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class CartResponse {
    private Long id;
    private List<TestCartEntry> shoppingList;
    private boolean payed;

    public CartResponse(Cart cart) {
        this.id = cart.getId();
        this.shoppingList = cart.getShoppingList().stream().map(item -> {
            TestCartEntry entry = new TestCartEntry();
            entry.setProductId(item.getProduct().getId());
            entry.setAmount(item.getQuantity());
            return entry;
        }).collect(Collectors.toList());
        this.payed = cart.isPayed();
    }
}

