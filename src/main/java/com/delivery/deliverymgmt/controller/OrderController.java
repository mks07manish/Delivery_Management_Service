package com.delivery.deliverymgmt.controller;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.delivery.deliverymgmt.entity.DeliveryStatus;
import com.delivery.deliverymgmt.entity.Order;
import com.delivery.deliverymgmt.exceptions.ErrorResponse;
import com.delivery.deliverymgmt.service.OrderService;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/api/v1/orders")
    public ResponseEntity<Order> addOrder(@RequestBody Order order) {
        // System.out.println(order);
        return ResponseEntity.ok(orderService.createOrder(order));
    }

    @GetMapping("/api/v1/orders")
    public ResponseEntity<Page<Order>> getOrdersByStatus(
        @RequestParam DeliveryStatus status,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
            Pageable pageable = PageRequest.of(page, size);
            return ResponseEntity.ok(orderService.getOrdersByStatus(status, pageable));
    }

    @GetMapping("/api/v1/orders/all")
    public ResponseEntity<Page<Order>> allOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orders = orderService.getAllOrders(pageable);
        
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/api/v1/customers/top")
    public ResponseEntity<List<Object[]>> getTopCustomersByDeliveredOrders() {
        return ResponseEntity.ok(orderService.getTopCustomersByDeliveredOrders());
    }

    @GetMapping("/api/v1/orders/status-count")
    public ResponseEntity<Map<String, Long>> getOrderStatusCount() {
        return ResponseEntity.ok(orderService.getOrderStatusCount());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Invalid input",
            ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Internal server error",
            ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Unexpected error",
            ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}
