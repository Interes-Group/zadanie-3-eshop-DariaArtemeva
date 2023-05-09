package sk.stuba.fei.uim.oop.assignment3.cart;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping
    public ResponseEntity<List<Cart>> getAllCarts() {
        return ResponseEntity.ok(cartService.getAllCarts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCartById(@PathVariable Long id) {
        return cartService.getCartById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cart> createCart() {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.createCart());
    }

    @PostMapping("/{cartId}/add")
    public ResponseEntity<CartItem> addProductToCart(@PathVariable Long cartId, @RequestBody AddProductRequest request) {
        return cartService.addProductToCart(cartId, request.getProductId(), request.getAmount())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        cartService.deleteCart(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/pay")
    public ResponseEntity<Void> payForCart(@PathVariable Long id) {
        Optional<Cart> paidCart = cartService.payForCart(id);
        if (paidCart.isPresent()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}