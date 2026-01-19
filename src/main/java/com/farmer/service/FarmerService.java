package com.farmer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.farmer.dto.FarmerLoginRequest;
import com.farmer.entity.Farmer;
import com.farmer.repository.FarmerRepository;

@Service
public class FarmerService {

    @Autowired
    private FarmerRepository farmerRepository;

    // Register
    public Farmer registerFarmer(Farmer farmer) {
        return farmerRepository.save(farmer);
    }

    // Login
    public Farmer loginFarmer(FarmerLoginRequest loginRequest) {

        Farmer farmer = farmerRepository.findByEmail(loginRequest.getEmail());

        if (farmer == null) {
            throw new RuntimeException("Farmer not found");
        }

        if (!farmer.getPassword().equals(loginRequest.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return farmer;
    }
    
    public Farmer getFarmerById(int farmerId) {
        return farmerRepository.findById(farmerId)
                .orElseThrow(() -> new RuntimeException("Farmer not found"));
    }

    public Farmer updateFarmerProfile(int farmerId, Farmer updated) {
        Farmer existing = farmerRepository.findById(farmerId)
                .orElseThrow(() -> new RuntimeException("Farmer not found"));

        if(updated.getName() != null)
            existing.setName(updated.getName());

        if(updated.getEmail() != null)
            existing.setEmail(updated.getEmail());

        if(updated.getPhone() != null)
            existing.setPhone(updated.getPhone());

        if(updated.getLocation() != null)
            existing.setLocation(updated.getLocation());
        
        if (updated.getPassword() != null && updated.getPassword().length() >= 6) {
            existing.setPassword(updated.getPassword());
        }
        return farmerRepository.save(existing);
    }


}
