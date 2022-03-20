package com.jpan.retail.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;


/**
 * Book object, it is data mapping with Table Book.
 * id: book id
 * name: book name
 * quantity: the quantity of the book
 * price: price of the book
 * description: description of the book
 */
@Entity
public class Book implements Serializable {

    private static final long serialVersionUID = 6467681785692114077L;

    @Id
    @GeneratedValue()
    private long id;
    private String name;
    private Integer quantity;
    private BigDecimal price;
    private String description;

    public Book() {
    }

    private Book(Builder builder) {
        this.name = builder.name;
        this.quantity = builder.quantity;
        this.price = builder.price;
        this.description = builder.descirption;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static class Builder {

        private String name;
        private Integer quantity;
        private BigDecimal price;
        private String descirption;

        public Builder(String name) {
            this.name = name;
        }

        public Builder quantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder descirption(String descirption) {
            this.descirption = descirption;
            return this;
        }

        public Book build() {
            return new Book(this);
        }
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", description=" + description +
                '}';
    }
}
