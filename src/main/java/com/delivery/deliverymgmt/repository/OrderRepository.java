package com.delivery.deliverymgmt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import com.delivery.deliverymgmt.entity.DeliveryStatus;
import com.delivery.deliverymgmt.entity.Order;

// import jakarta.persistence.LockModeType;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findAll(Pageable pageable);

    Page<Order> findByDeliveryStatus(DeliveryStatus status, Pageable pageable);

    @Query("SELECT o.customerName, COUNT(o) AS orderCount FROM Order o WHERE o.deliveryStatus = 'DELIVERED' GROUP BY o.customerName ORDER BY orderCount DESC LIMIT 3")
    List<Object[]> findTopCustomersByDeliveredOrders();

    // Fetch count of orders for each delivery status
    @Query("SELECT o.deliveryStatus, COUNT(o) FROM Order o GROUP BY o.deliveryStatus")
    List<Object[]> getOrderStatusCount();

    // @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT o FROM Order o WHERE o.orderId = :orderId")
    Optional<Order> findOrderForProcessing(Long orderId);
}
