package com.jpan.retail.model.request;

import com.jpan.retail.entity.Book;

import java.util.List;

public class OrderRequest {

    private long memberId;
    private List<Book> books;


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
}
