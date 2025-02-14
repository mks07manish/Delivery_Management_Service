package com.delivery.deliverymgmt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.delivery.deliverymgmt.entity.DeliveryStatus;
import com.delivery.deliverymgmt.entity.Order;
import com.delivery.deliverymgmt.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public Page<Order> getOrdersByStatus(DeliveryStatus status, Pageable pageable) {
        return orderRepository.findByDeliveryStatus(status, pageable);
    }

    public Page<Order> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    public List<Object[]> getTopCustomersByDeliveredOrders() {
        return orderRepository.findTopCustomersByDeliveredOrders();
    }

    public Map<String, Long> getOrderStatusCount() {
        List<Object[]> results = orderRepository.getOrderStatusCount();
        Map<String, Long> statusCount = new HashMap<>();

        // Converting result into a key-value map
        for (Object[] result : results) {
            String status = result[0].toString();
            Long count = (Long) result[1];
            statusCount.put(status, count);
        }

        return statusCount;
    }
}
