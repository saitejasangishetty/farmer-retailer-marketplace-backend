package com.farmer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.farmer.entity.Retailer;

public interface RetailerRepository extends JpaRepository<Retailer, Integer> {

    Retailer findByEmail(String email);
}
