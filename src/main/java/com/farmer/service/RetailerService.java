package com.farmer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.farmer.entity.Retailer;
import com.farmer.repository.RetailerRepository;
import com.farmer.dto.RetailerLoginRequest;

@Service
public class RetailerService {

    @Autowired
    private RetailerRepository retailerRepository;

    public Retailer registerRetailer(Retailer retailer) {
        return retailerRepository.save(retailer);
    }
    public Retailer loginRetailer(RetailerLoginRequest loginRequest) {

        Retailer retailer = retailerRepository.findByEmail(loginRequest.getEmail());

        if (retailer == null) {
            throw new RuntimeException("Retailer not found with this email");
        }

        if (!retailer.getPassword().equals(loginRequest.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return retailer;
    }
    
    public Retailer getRetailerById(int retailerId) {
        return retailerRepository.findById(retailerId)
                .orElseThrow(() -> new RuntimeException("Retailer not found"));
    }

    public Retailer updateRetailerProfile(int retailerId, Retailer updated) {
        Retailer existing = retailerRepository.findById(retailerId)
                .orElseThrow(() -> new RuntimeException("Retailer not found"));

        if(updated.getName() != null)
            existing.setName(updated.getName());

        if(updated.getEmail() != null)
            existing.setEmail(updated.getEmail());

        if(updated.getPhone() != null)
            existing.setPhone(updated.getPhone());

        if(updated.getShopName() != null)
            existing.setShopName(updated.getShopName());

        if(updated.getLocation() != null)
            existing.setLocation(updated.getLocation());

        return retailerRepository.save(existing);
    }


}
