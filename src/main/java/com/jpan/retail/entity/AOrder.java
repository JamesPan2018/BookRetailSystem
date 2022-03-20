package com.jpan.retail.entity;


import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Order object, it is data mapping with Table AOrder.
 * id: order id
 * memberId: the member id which submit this order
 * books: the books which are included in this order
 * dateCreated: the time stamp of the order creation time
 * totalPrice: total price of the order
 */
@Entity
public class AOrder implements Serializable {

    private static final long serialVersionUID = 8961295495660364471L;

    @Id
    @GeneratedValue
    private long id;

    private long memberId;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Book> books;

    @CreationTimestamp
    private LocalDateTime dateCreated;

    private BigDecimal totalPrice;


    public AOrder() {
    }

    public AOrder(long memberId, List<Book> books, LocalDateTime dateCreated, BigDecimal totalPrice) {
        this.memberId = memberId;
        this.books = books;
        this.dateCreated = dateCreated;
        this.totalPrice = totalPrice;
    }

    public AOrder(long id, long memberId, List<Book> books, LocalDateTime dateCreated, BigDecimal totalPrice) {
        this.id = id;
        this.memberId = memberId;
        this.books = books;
        this.dateCreated = dateCreated;
        this.totalPrice = totalPrice;

    }

    public long getId() {
        return id;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", books=" + books +
                ", dateCreated=" + dateCreated +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
