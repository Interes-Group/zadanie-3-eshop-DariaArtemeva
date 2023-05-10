package sk.stuba.fei.uim.oop.assignment3.cart;

public class AddProductRequest {
    private Long productId;
    private Long amount;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
