package sk.stuba.fei.uim.oop.assignment3.product;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public  class Amount {
    private int amount;

    public Amount() {
    }

    public Amount(int amount) {
        this.amount = amount;
    }
}

