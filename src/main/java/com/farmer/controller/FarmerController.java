package com.farmer.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.farmer.dto.FarmerLoginRequest;
import com.farmer.entity.Farmer;
import com.farmer.entity.Product;
import com.farmer.service.FarmerService;
import com.farmer.service.ProductService;

@CrossOrigin(origins = "*")   // 🔥 ALLOW EVERYTHING (FOR DEV)
@RestController
@RequestMapping("/farmer")
public class FarmerController {

    @Autowired
    private FarmerService farmerService;

    @Autowired
    private ProductService productService;

    @PostMapping("/register")
    public Farmer registerFarmer(@RequestBody Farmer farmer) {
        return farmerService.registerFarmer(farmer);
    }

    @PostMapping("/login")
    public Farmer loginFarmer(@RequestBody FarmerLoginRequest loginRequest) {
        return farmerService.loginFarmer(loginRequest);
    }

    @PostMapping("/{farmerId}/add-product")
    public Product addProduct(
            @PathVariable int farmerId,
            @RequestParam("productName") String productName,
            @RequestParam("price") double price,
            @RequestParam("quantity") int quantity,
            @RequestParam("image") MultipartFile image
    ) throws IOException {

        Product product = new Product();
        product.setProductName(productName);
        product.setPrice(price);
        product.setQuantity(quantity);

        return productService.addProductWithImage(farmerId, product, image);
    }

    @GetMapping("/{farmerId}/products")
    public List<Product> getProducts(@PathVariable int farmerId) {
        return productService.getProductsByFarmer(farmerId);
    }
    
 // ✅ Get Farmer Profile
    @GetMapping("/profile/{farmerId}")
    public Farmer getFarmerProfile(@PathVariable int farmerId) {
        return farmerService.getFarmerById(farmerId);
    }

    // ✅ Update Farmer Profile
    @PutMapping("/profile/{farmerId}")
    public Farmer updateFarmerProfile(
            @PathVariable int farmerId,
            @RequestBody Farmer updatedFarmer) {

        return farmerService.updateFarmerProfile(farmerId, updatedFarmer);
    }

}
