package com.farmer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.farmer.entity.Order;
import com.farmer.entity.Product;
import com.farmer.entity.Retailer;
import com.farmer.repository.OrderRepository;
import com.farmer.repository.ProductRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    // ✅ Save order
    public Order placeOrder(Order order) {
        order.setStatus("PLACED");
        return orderRepository.save(order);
    }

    // ✅ Retailer orders
    public List<Order> getOrdersByRetailer(Retailer retailer) {
        return orderRepository.findByRetailer(retailer);
    }

    // ✅ Farmer orders (based on product ownership)
    public List<Order> getOrdersByFarmer(Integer farmerId) {
        return orderRepository.findAll()
                .stream()
                .filter(o -> o.getProduct().getFarmer().getFarmerId() == farmerId)
                .toList();
    }

    // ✅ Strict Status Update Logic
    public Order updateStatus(Integer orderId, String newStatus) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        String currentStatus = order.getStatus();

        // 🚦 Status rules
        if (newStatus.equals("ACCEPTED") && !currentStatus.equals("PLACED")) {
            throw new RuntimeException("Only PLACED orders can be ACCEPTED");
        }

        if (newStatus.equals("DISPATCHED") && !currentStatus.equals("ACCEPTED")) {
            throw new RuntimeException("Only ACCEPTED orders can be DISPATCHED");
        }

        if (newStatus.equals("DELIVERED") && !currentStatus.equals("DISPATCHED")) {
            throw new RuntimeException("Only DISPATCHED orders can be DELIVERED");
        }

        order.setStatus(newStatus);
        return orderRepository.save(order);
    }

    // ✅ Cancel Order (Only when PLACED)
    public Order cancelOrder(int orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getStatus().equals("PLACED")) {
            throw new RuntimeException("Only PLACED orders can be cancelled");
        }

        // 🔁 Rollback quantity
        Product product = order.getProduct();
        product.setQuantity(product.getQuantity() + order.getQuantity());
        productRepository.save(product);

        order.setStatus("CANCELLED");
        return orderRepository.save(order);
    }
}
