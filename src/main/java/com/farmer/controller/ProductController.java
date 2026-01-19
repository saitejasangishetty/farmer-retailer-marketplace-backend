package com.farmer.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.farmer.entity.Product;
import com.farmer.service.ProductService;
import com.farmer.repository.ProductRepository;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {
	
	@Autowired
	private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    // ✅ ADD PRODUCT WITH IMAGE
    @PostMapping("/add/{farmerId}")
    public Product addProduct(
            @PathVariable int farmerId,
            @RequestParam("productName") String productName,
            @RequestParam("price") double price,
            @RequestParam("quantity") int quantity,
            @RequestParam("category") String category,
            @RequestParam("location") String location,
            @RequestParam("image") MultipartFile image
    ) throws IOException {

        Product product = new Product();
        product.setProductName(productName);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setCategory(category);     // ✅ VERY IMPORTANT
        product.setLocation(location);     // ✅ VERY IMPORTANT

        return productService.addProductWithImage(farmerId, product, image);
    }


    // ✅ FARMER PRODUCTS
    @GetMapping("/farmer/{farmerId}")
    public List<Product> getFarmerProducts(@PathVariable int farmerId) {
        return productService.getProductsByFarmer(farmerId);
    }

    // ✅ ALL PRODUCTS
    @GetMapping("/all")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
    
 // ✅ Get Only Available Products (quantity > 0)
    @GetMapping("/available")
    public List<Product> getAvailableProducts() {
        return productRepository.findByQuantityGreaterThan(0);
    }
    
    @GetMapping("/search")
    public List<Product> searchProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        return productRepository.liveSearch(
                category,
                location,
                minPrice,
                maxPrice
        );
    }



}
