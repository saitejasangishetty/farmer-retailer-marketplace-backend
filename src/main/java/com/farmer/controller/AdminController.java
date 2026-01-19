package com.farmer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.farmer.entity.Farmer;
import com.farmer.entity.Retailer;
import com.farmer.entity.Product;
import com.farmer.repository.FarmerRepository;
import com.farmer.repository.RetailerRepository;
import com.farmer.repository.ProductRepository;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    @Autowired
    private FarmerRepository farmerRepository;

    @Autowired
    private RetailerRepository retailerRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/farmers")
    public List<Farmer> getAllFarmers() {
        return farmerRepository.findAll();
    }

    @GetMapping("/retailers")
    public List<Retailer> getAllRetailers() {
        return retailerRepository.findAll();
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
