package com.jpan.retail.service;

import com.jpan.retail.entity.AOrder;
import com.jpan.retail.repo.OrderRepository;
import com.jpan.retail.entity.Book;
import com.jpan.retail.model.request.OrderRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
public class OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final MemberService memberService;

    @Autowired
    public OrderService(OrderRepository orderRepository, MemberService memberService) {
        this.orderRepository = orderRepository;
        this.memberService = memberService;
    }

    /**
     * Create a book order and saved into database, also will update the member's point.
     *
     * @param request OrderRequest
     * @return Order
     */
    public AOrder submitOrder(OrderRequest request) {
        final List<Book> books = request.getBooks();
        final long memberId = request.getMemberId();
        final BigDecimal totalPrices = calculateTotalPrices(books);

        AOrder AOrder = new AOrder();
        AOrder.setMemberId(memberId);
        AOrder.setBooks(books);
        AOrder.setTotalPrice(totalPrices);

        AOrder resOrder = saveOrder(AOrder);
        LOGGER.info(String.format("Success to save the order into database, order id: %s", resOrder.getId()));
        return resOrder;
    }

    private BigDecimal calculateTotalPrices(List<Book> books) {
        BigDecimal totalPrice = new BigDecimal(0);
        for (Book book : books) {
            BigDecimal price = book.getPrice();
            BigDecimal quantity = BigDecimal.valueOf(book.getQuantity());
            totalPrice = totalPrice.add(price.multiply(quantity));
        }
        LOGGER.info(String.format("Total price of the order is: %s", totalPrice));
        return totalPrice;
    }

    public AOrder saveOrder(AOrder AOrder) {
        memberService.updateMemberPoint(AOrder);
        return orderRepository.save(AOrder);
    }

    /**
     * Find all orders from database.
     *
     * @return List<AOrder>
     */
    public List<AOrder> queryOrders() {
        return orderRepository.findAll();
    }

}
