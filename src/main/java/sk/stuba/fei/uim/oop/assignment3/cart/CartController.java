package sk.stuba.fei.uim.oop.assignment3.cart;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import sk.stuba.fei.uim.oop.assignment3.product.*;

import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private CartItemRepository cartItemRepository;

    public CartController(CartRepository cartRepository, ProductRepository productRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @PostMapping
    public ResponseEntity<CartResponse> createCart() {
        Cart cart = new Cart();
        cartRepository.save(cart);
        CartResponse cartResponse = new CartResponse(cart);
        return new ResponseEntity<>(cartResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartResponse> getCartById(@PathVariable Long id) {
        return cartRepository.findById(id)
                .map(cart -> new ResponseEntity<>(new CartResponse(cart), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartById(@PathVariable Long id) {
        if (cartRepository.existsById(id)) {
            cartRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{cartId}/add")
    public ResponseEntity<CartResponseWithoutIds> addProductToCart(@PathVariable Long cartId,
                                                                   @RequestBody AddProductRequest addProductRequest) {
        Long productId = addProductRequest.getProductId();
        Long quantity = addProductRequest.getAmount();
        Optional<Cart> cartOpt = cartRepository.findById(cartId);
        Optional<Product> productOpt = productRepository.findById(productId);

        if (cartOpt.isPresent() && productOpt.isPresent()) {
            Cart cart = cartOpt.get();
            Product product = productOpt.get();

            // проверка, была ли корзина оплачена
            if (cart.isPayed()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            if (product.getAmount() < quantity) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            if (cart == null || product == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            CartItem cartItem = cart.getShoppingList().stream()
                    .filter(item -> item.getProduct().getId() == productId.intValue())
                    .findFirst()
                    .orElse(null);

            if (cartItem != null) {
                cartItem.setQuantity(cartItem.getQuantity() + quantity.intValue());
            } else {
                cartItem = new CartItem(product, quantity.intValue());
                cart.getShoppingList().add(cartItem);
                // Сохранение нового CartItem
                cartItemRepository.save(cartItem);
            }

            product.setAmount(product.getAmount() - quantity.intValue());
            productRepository.save(product);
            cartRepository.save(cart);

            CartResponseWithoutIds cartResponse = new CartResponseWithoutIds(cart);
            return ResponseEntity.ok(cartResponse);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{cartId}/pay")
    public ResponseEntity<String> payForCart(@PathVariable Long cartId) {
        Optional<Cart> cartOpt = cartRepository.findById(cartId);

        if (cartOpt.isPresent()) {
            Cart cart = cartOpt.get();

            if (cart.isPayed()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            double totalPrice = cart.getShoppingList().stream()
                    .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                    .sum();

            cart.setPayed(true);
            cartRepository.save(cart);
            return ResponseEntity.ok(String.format(Locale.US, "%.2f", totalPrice));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
