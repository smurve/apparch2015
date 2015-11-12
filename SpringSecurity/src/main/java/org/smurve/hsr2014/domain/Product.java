package org.smurve.hsr2014.domain;

import org.smurve.hsr2014.ProductType;

import javax.persistence.*;

@Entity
@Table ( name = "products")
public class Product {

    @Id
    private String productId;

    private String name;

    @Enumerated(EnumType.STRING)
    private ProductType type;

    private Product() {
    } // for JPA

    public Product(String productId, String name, ProductType type) {
        this.productId = productId;
        this.name = name;
        this.type = type;
    }

    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public ProductType getType() {
        return type;
    }
}

