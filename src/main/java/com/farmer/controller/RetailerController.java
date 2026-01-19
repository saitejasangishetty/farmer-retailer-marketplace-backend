package com.farmer.controller;
import com.farmer.dto.RetailerLoginRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.farmer.entity.Retailer;
import com.farmer.service.RetailerService;
import jakarta.validation.Valid;
@RestController
@RequestMapping("/retailer")
@CrossOrigin(origins = "http://localhost:3000")
public class RetailerController {

    @Autowired
    private RetailerService retailerService;

    @PostMapping("/register")
    public Retailer register(@Valid @RequestBody Retailer retailer) {
        return retailerService.registerRetailer(retailer);
    }
    @PostMapping("/login")
    public Retailer loginRetailer(@Valid @RequestBody RetailerLoginRequest loginRequest) {
        return retailerService.loginRetailer(loginRequest);
    }
    
 // ✅ Get Retailer Profile
    @GetMapping("/profile/{retailerId}")
    public Retailer getRetailerProfile(@PathVariable int retailerId) {
        return retailerService.getRetailerById(retailerId);
    }

    // ✅ Update Retailer Profile
    @PutMapping("/profile/{retailerId}")
    public Retailer updateRetailerProfile(
            @PathVariable int retailerId,
            @RequestBody Retailer updatedRetailer) {

        return retailerService.updateRetailerProfile(retailerId, updatedRetailer);
    }


}
