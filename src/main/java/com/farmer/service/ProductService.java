package com.farmer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.farmer.entity.Farmer;
import com.farmer.entity.Product;
import com.farmer.repository.FarmerRepository;
import com.farmer.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FarmerRepository farmerRepository;

    // ✅ Add product with image
    public Product addProductWithImage(int farmerId, Product product, MultipartFile imageFile) throws IOException {

        Farmer farmer = farmerRepository.findById(farmerId)
                .orElseThrow(() -> new RuntimeException("Farmer not found"));

        String uploadDir = "uploads/";

        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
        Path filePath = Paths.get(uploadDir + fileName);
        Files.write(filePath, imageFile.getBytes());

        product.setImage(fileName);
        product.setFarmer(farmer);

        return productRepository.save(product);
    }

    // ✅ Farmer products
    public List<Product> getProductsByFarmer(int farmerId) {
        Farmer farmer = farmerRepository.findById(farmerId)
                .orElseThrow(() -> new RuntimeException("Farmer not found"));
        return productRepository.findByFarmer(farmer);
    }

    // ✅ All products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}

