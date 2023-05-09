package sk.stuba.fei.uim.oop.assignment3.cart;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
@Getter
@Setter
@Embeddable
public class EmbeddedProduct {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private int amount;

}
