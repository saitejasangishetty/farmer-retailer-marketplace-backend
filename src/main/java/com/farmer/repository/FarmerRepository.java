package com.farmer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.farmer.entity.Farmer;

public interface FarmerRepository extends JpaRepository<Farmer, Integer> {

    Farmer findByEmail(String email);
}
