package com.jpan.retail.controller;

import com.jpan.retail.entity.AOrder;
import com.jpan.retail.model.request.OrderRequest;
import com.jpan.retail.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Submit a order request, it will store the order into database
     * and also update the member's point by following the MemberType.
     *
     * @param request order request
     * @return common response entity
     */
    @PostMapping("/submitOrder")
    public ResponseEntity<AOrder> submitOrder(@RequestBody OrderRequest request) {
        return new ResponseEntity<>(orderService.submitOrder(request), HttpStatus.OK);
    }

    /**
     * Get all the orders from database.
     * @return common response entity
     */
    @GetMapping("/queryOrders")
    public ResponseEntity<List<AOrder>> queryOrders() {
        return new ResponseEntity<>(orderService.queryOrders(), HttpStatus.OK);
    }
}
