package com.mgt.service;

import com.mgt.model.Product;
import com.mgt.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Override
    public boolean addPro(Product product, MultipartFile productImage) {
        try {
            // Store the image and get file path
            String filePath = storeImage(productImage);

            // Save file path to database
            product.setProduct_image(filePath);

            // Save product to database
            productRepo.save(product);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String storeImage(MultipartFile file) throws IOException {
        String directory = "D:/MGT/uploads/"; // Change this path as needed
        File dir = new File(directory);
        if (!dir.exists()) {
            dir.mkdirs(); // Create directory if not exists
        }

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        String filePath = directory + fileName;

        // Save file to the server
        file.transferTo(new File(filePath));

        return filePath;
    }

    @Override
    public Product getProductById(Integer product_id) {
        return productRepo.findById(product_id).orElse(null);
    }

    @Override
    public List<Product> getAllPro() {
        return productRepo.findAll();
    }

    @Override
    public Product updatePro(Product product, MultipartFile image) {
        Optional<Product> optionalProduct = productRepo.findById(product.getProduct_id());

        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();

            // Update fields
            existingProduct.setProduct_name(product.getProduct_name());
            existingProduct.setProduct_price(product.getProduct_price());
            existingProduct.setProduct_category(product.getProduct_category());
            existingProduct.setProduct_available_stock_quantity(product.getProduct_available_stock_quantity());
            existingProduct.setProduct_description(product.getProduct_description());

            // Update image if a new one is provided
            if (image != null && !image.isEmpty()) {
                try {
                    String imagePath = storeImage(image);
                    existingProduct.setProduct_image(imagePath);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to save image", e);
                }
            }

            return productRepo.save(existingProduct);
        } else {
            throw new RuntimeException("Product not found with id: " + product.getProduct_id());
        }
    }

    @Override
    public boolean deletePro(Integer product_id) {
        boolean status = false;
        if (product_id != null) {
            productRepo.deleteById(product_id);
            status = true;
        }
        return status;
    }
}
