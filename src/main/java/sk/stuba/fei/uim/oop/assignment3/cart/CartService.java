package sk.stuba.fei.uim.oop.assignment3.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.product.Product;
import sk.stuba.fei.uim.oop.assignment3.product.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public Optional<Cart> getCartById(Long id) {
        return cartRepository.findById(id);
    }

    public Cart createCart() {
        Cart cart = new Cart();
        return cartRepository.save(cart);
    }

    public Optional<CartItem> addProductToCart(Long cartId, Long productId, int quantity) {
        return cartRepository.findById(cartId).flatMap(cart -> productRepository.findById(productId).map(product -> {
            if (!cart.isPayed() && product.getAmount() >= quantity) {
                CartItem cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setQuantity(quantity);
                cart.getShoppingList().add(cartItem);
                product.setAmount(product.getAmount() - quantity);
                productRepository.save(product);
                cartRepository.save(cart);
                return cartItem;
            } else {
                return null;
            }
        }));
    }

    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }

    public boolean payForCart(Long id) {
        return cartRepository.findById(id).map(cart -> {
            if (!cart.isPayed()) {
                cart.setPayed(true);
                cartRepository.save(cart);
                return true;
            } else {
                return false;
            }
        }).orElse(false);
    }
}

