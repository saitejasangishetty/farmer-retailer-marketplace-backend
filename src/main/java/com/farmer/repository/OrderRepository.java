package com.farmer.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.farmer.entity.Order;
import com.farmer.entity.Product;
import com.farmer.entity.Retailer;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByRetailer(Retailer retailer);

    List<Order> findByProduct(Product product);

}
