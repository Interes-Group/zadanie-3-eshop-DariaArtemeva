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

    @Autowired
    private CartItemRepository cartItemRepository;

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

                // Создаем объект EmbeddedProduct и копируем данные из Product
                EmbeddedProduct embeddedProduct = new EmbeddedProduct();
                embeddedProduct.setId(product.getId());
                embeddedProduct.setName(product.getName());
                embeddedProduct.setDescription(product.getDescription());
                embeddedProduct.setPrice(product.getPrice());
                embeddedProduct.setAmount(quantity);

                cartItem.setProduct(embeddedProduct);
                cartItem.setQuantity(quantity);
                cartItem.setCart(cart);
                cartItem = cartItemRepository.save(cartItem);
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

    public Optional<Cart> payForCart(Long id) {
        return cartRepository.findById(id).flatMap(cart -> {
            if (!cart.isPayed()) {
                cart.setPayed(true);
                cartRepository.save(cart);
                return Optional.of(cart);
            } else {
                return Optional.empty();
            }
        });
    }

}

