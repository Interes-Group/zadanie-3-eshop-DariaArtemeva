package sk.stuba.fei.uim.oop.assignment3.cart;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class CartResponse {
    private Long id;
    private List<CartItem> shoppingList;
    private boolean payed;

    // Конструктор, преобразующий Cart в CartResponse
    public CartResponse(Cart cart) {
        this.id = cart.getId();
        this.shoppingList = cart.getShoppingList();
        this.payed = cart.isPayed();
    }

    // Геттеры и сеттеры
}
