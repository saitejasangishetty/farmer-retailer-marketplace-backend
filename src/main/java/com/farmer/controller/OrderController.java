package com.farmer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.farmer.entity.Order;
import com.farmer.entity.Product;
import com.farmer.entity.Retailer;
import com.farmer.repository.ProductRepository;
import com.farmer.repository.RetailerRepository;
import com.farmer.service.OrderService;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private RetailerRepository retailerRepository;

    @Autowired
    private ProductRepository productRepository;

    // ✅ Place Order
    @PostMapping("/place")
    public Order placeOrder(
            @RequestParam Integer retailerId,
            @RequestParam Integer productId,
            @RequestParam int quantity){

        Retailer retailer = retailerRepository.findById(retailerId)
                .orElseThrow(() -> new RuntimeException("Retailer not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // 🔴 Stock validation
        if(quantity > product.getQuantity()) {
            throw new RuntimeException("Not enough stock");
        }

        // ✅ Reduce stock
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);

        Order order = new Order();
        order.setRetailer(retailer);
        order.setProduct(product);
        order.setQuantity(quantity);
        order.setTotalPrice(product.getPrice() * quantity);
        order.setStatus("PLACED");

        return orderService.placeOrder(order);
    }

    // ✅ Orders by Retailer
    @GetMapping("/retailer/{retailerId}")
    public List<Order> getOrdersByRetailer(@PathVariable Integer retailerId) {

        Retailer retailer = retailerRepository.findById(retailerId)
                .orElseThrow(() -> new RuntimeException("Retailer not found"));

        return orderService.getOrdersByRetailer(retailer);
    }
 // ✅ Get Orders By Farmer
    @GetMapping("/farmer/{farmerId}")
    public List<Order> getOrdersByFarmer(@PathVariable Integer farmerId) {
        return orderService.getOrdersByFarmer(farmerId);
    }
    
 // ✅ Update Order Status
    @PostMapping("/update-status")
    public Order updateStatus(
            @RequestParam Integer orderId,
            @RequestParam String status) {

        return orderService.updateStatus(orderId, status);
    }
    @PostMapping("/cancel")
    public Order cancelOrder(@RequestParam int orderId) {
        return orderService.cancelOrder(orderId);
    }

}
