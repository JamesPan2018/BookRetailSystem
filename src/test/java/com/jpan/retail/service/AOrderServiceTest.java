package com.jpan.retail.service;

import com.jpan.retail.entity.AOrder;
import com.jpan.retail.entity.Book;
import com.jpan.retail.entity.Member;
import com.jpan.retail.enums.MemberType;
import com.jpan.retail.model.request.OrderRequest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AOrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private MemberService memberService;

    @Test
    public void submitOrder() {

        Member member = createNewMember();
        long memberId = member.getId();
        BigDecimal memberPoint = member.getPoint();
        MemberType membertype = member.getMemberType();

        List<Book> books = new ArrayList<>();
        Book book1 = new Book.Builder("Book1").price(BigDecimal.valueOf(10.01)).quantity(10).descirption("This is Book1").build();
        Book book2 = new Book.Builder("Book2").price(BigDecimal.valueOf(11.11)).quantity(10).descirption("This is Book2").build();
        Book book3 = new Book.Builder("Book3").price(BigDecimal.valueOf(12.21)).quantity(10).descirption("This is Book3").build();
        books.add(book1);
        books.add(book2);
        books.add(book3);

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setMemberId(memberId);
        orderRequest.setBooks(books);

        AOrder order = orderService.submitOrder(orderRequest);
        Assert.assertEquals("Member ID should be same.", order.getMemberId(), memberId);

        List<Book> booksFromOrder = order.getBooks();
        Assert.assertEquals("Books size should be same.", booksFromOrder.size(), books.size());
        int count = booksFromOrder.size();
        for (Book bookFromOrder : booksFromOrder) {
            String bookNameFromOrder = bookFromOrder.getName();
            for (Book book : books) {
                if (bookNameFromOrder.equals(book.getName())) {
                    Assert.assertEquals("The two book name should be equals.", bookFromOrder.getName(), book.getName());
                    Assert.assertEquals("The two book price should be equals.", bookFromOrder.getPrice(), book.getPrice());
                    Assert.assertEquals("The two book quantity should be equals.", bookFromOrder.getQuantity(), book.getQuantity());
                    Assert.assertEquals("The two book descirption should be equals.", bookFromOrder.getDescription(), book.getDescription());
                    --count;
                }
            }
        }
        Assert.assertEquals("All the books should be equals.", 0, count);

        BigDecimal totalPrices = calculateTotalPrices(books);
        BigDecimal totalPoint = calculateMemberPoint(membertype, totalPrices);
        BigDecimal updatedMemberPoint = memberPoint.add(totalPoint);

        Member memberFromDB = memberService.getMemberById(memberId);
        Assert.assertNotNull("The specified member object should exists.", memberFromDB);
        Assert.assertEquals("The new Member points should be equals.", memberFromDB.getPoint(), updatedMemberPoint);
    }

    @Test
    public void queryOrders() {
        OrderRequest orderRequest1 = createOrderRequest();
        OrderRequest orderRequest2 = createOrderRequest();
        OrderRequest orderRequest3 = createOrderRequest();
        orderService.submitOrder(orderRequest1);
        orderService.submitOrder(orderRequest2);
        orderService.submitOrder(orderRequest3);
        List<AOrder> orders = orderService.queryOrders();
        Assert.assertEquals("The count number of orders should equals to created numbers.", 3, orders.size());
    }

    private OrderRequest createOrderRequest() {
        Member member = createNewMember();
        long memberId = member.getId();

        List<Book> books = new ArrayList<>();
        Book book1 = new Book.Builder("Book1").price(BigDecimal.valueOf(10.01)).quantity(10).descirption("This is Book1").build();
        Book book2 = new Book.Builder("Book2").price(BigDecimal.valueOf(11.11)).quantity(10).descirption("This is Book2").build();
        Book book3 = new Book.Builder("Book3").price(BigDecimal.valueOf(12.21)).quantity(10).descirption("This is Book3").build();
        books.add(book1);
        books.add(book2);
        books.add(book3);

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setMemberId(memberId);
        orderRequest.setBooks(books);
        return orderRequest;
    }

    private Member createNewMember() {
        Member member = new Member();
        member.setName("MemberA");
        member.setPoint(BigDecimal.valueOf(12.23));
        member.setMemberType(MemberType.GOLD);

        return memberService.createMember(member);
    }

    private BigDecimal calculateTotalPrices(List<Book> books) {
        BigDecimal totalPrice = new BigDecimal(0);
        for (Book book : books) {
            BigDecimal price = book.getPrice();
            BigDecimal quantity = BigDecimal.valueOf(book.getQuantity());
            totalPrice = totalPrice.add(price.multiply(quantity));
        }
        return totalPrice;
    }

    private BigDecimal calculateMemberPoint(MemberType type, BigDecimal totalPrices) {
        int multiplier = -1;
        switch (type) {
            case GOLD:
                multiplier = MemberType.GOLD.getMultiplier();
                break;
            case SILVER:
                multiplier = MemberType.SILVER.getMultiplier();
                break;
            case COPER:
                multiplier = MemberType.COPER.getMultiplier();
                break;
        }
        return totalPrices.multiply(BigDecimal.valueOf(multiplier));
    }
}