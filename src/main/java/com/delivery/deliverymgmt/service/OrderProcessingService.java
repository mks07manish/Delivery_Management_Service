package com.delivery.deliverymgmt.service;

import com.delivery.deliverymgmt.entity.DeliveryStatus;
import com.delivery.deliverymgmt.entity.Order;
import com.delivery.deliverymgmt.repository.OrderRepository;
import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class OrderProcessingService {

    private final OrderRepository orderRepository;
    private final ExecutorService executorService;

    public OrderProcessingService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        this.executorService = Executors.newFixedThreadPool(4); // 4 concurrent delivery agents
    }

    @Scheduled(cron = "0 */1 * * * *")
    public void processOrders() {
        System.out.println("Order processing started...");

        Pageable firstPageWithTwoOrders = PageRequest.of(0, 4);// size should be thread pool size
        Page<Order> pendingOrders = orderRepository.findByDeliveryStatus(DeliveryStatus.PENDING,
                firstPageWithTwoOrders);

        if (pendingOrders.isEmpty()) {
            System.out.println("No pending orders. Check again in a minutes...");
            return;
        }

        for (Order order : pendingOrders) {
            executorService.submit(() -> processOrder(order));
        }
    }

    @Transactional
    public void processOrder(Order order) {

        if (order.getDeliveryStatus() == DeliveryStatus.PENDING) {
            // Update status to IN_PROGRESS
            order.setDeliveryStatus(DeliveryStatus.IN_PROGRESS);
            orderRepository.save(order);
            System.out.println("Order " + order.getOrderId() + " is now IN_PROGRESS.");

            try {
                Thread.sleep(30000); // Simulate delivery time of half minute
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Update status to DELIVERED
            order.setDeliveryStatus(DeliveryStatus.DELIVERED);
            orderRepository.save(order);
            System.out.println("Order " + order.getOrderId() + " has been DELIVERED.");
        }
    }
}
