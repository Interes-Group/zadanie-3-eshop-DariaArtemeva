package sk.stuba.fei.uim.oop.assignment3.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Optional<Product> updateProduct(Long id, Product updatedProduct) {
        return productRepository.findById(id).map(product -> {
            if (updatedProduct.getName() != null) {
                product.setName(updatedProduct.getName());
            }
            if (updatedProduct.getDescription() != null) {
                product.setDescription(updatedProduct.getDescription());
            }
            if (updatedProduct.getAmount() != 0) {
                product.setAmount(updatedProduct.getAmount());
            }
            if (updatedProduct.getUnit() != null) {
                product.setUnit(updatedProduct.getUnit());
            }
            if (updatedProduct.getPrice() != 0) {
                product.setPrice(updatedProduct.getPrice());
            }
            return productRepository.save(product);
        });
    }



    public Optional<Product> deleteProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.deleteById(id);
        }
        return product;
    }


    public Optional<Amount> getProductAmountById(Long id) {
        return productRepository.findById(id)
                .map(product -> new Amount(product.getAmount()));
    }


    public Optional<Product> updateProductAmountById(Long id, int newAmount) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setAmount(newAmount);
                    return productRepository.save(product);
                });
    }

    public Optional<Product> incrementProductAmount(Long id, int incrementAmount) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setAmount(product.getAmount() + incrementAmount);
                    return productRepository.save(product);
                });
    }

}
