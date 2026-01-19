package com.farmer.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.farmer.entity.Farmer;
import java.util.List;
import com.farmer.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	List<Product> findByFarmer(Farmer farmer);
	
    // ✅ Only available products
    List<Product> findByQuantityGreaterThan(int qty);

    // ✅ LIVE SEARCH QUERY
    @Query("""
       SELECT p FROM Product p
       WHERE p.quantity > 0
       AND (:category IS NULL OR LOWER(p.category) LIKE LOWER(CONCAT('%', :category, '%')))
       AND (:location IS NULL OR LOWER(p.location) LIKE LOWER(CONCAT('%', :location, '%')))
       AND (:minPrice IS NULL OR p.price >= :minPrice)
       AND (:maxPrice IS NULL OR p.price <= :maxPrice)
    """)
    List<Product> liveSearch(
        @Param("category") String category,
        @Param("location") String location,
        @Param("minPrice") Double minPrice,
        @Param("maxPrice") Double maxPrice
    );
}
