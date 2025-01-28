package com.uce.product.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uce.product.Model.Product;
import com.uce.product.Model.Dto.ProductDto;
import com.uce.product.Repository.ProductRepository;
import com.uce.product.Service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/products")
@Tag(name = "Api Rest for brands use Swagger 3 - Product")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    @Operation(summary = "This endpoint is used for all products filtered by category and brand")
    public ResponseEntity<List<Product>> getAllProductsByCategoryAndBrand(
            @RequestParam Long idCategory, @RequestParam Long idBrand) {
        return ResponseEntity.ok(productService.getAllProduct(idCategory, idBrand));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping("/all")
    public ResponseEntity<Product> saveProduct(
            @RequestBody Product product) {
        return ResponseEntity.ok(productService.saveProducts(product));
    }

    @PostMapping
    @Operation(summary = "This endpoint is used to create a product")
    public ResponseEntity<Product> createProduct(
            @RequestParam Long idCategory, @RequestParam Long idBrand,
            @RequestBody Product product) {
        boolean created = productService.createProduct(idCategory, idBrand, product);
        if (created) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @PutMapping("/{id}/reduceStock")
    public ResponseEntity<Void> reduceStock(@PathVariable Long id, @RequestBody int quantity) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            if (product.getStock() >= quantity) {
                product.setStock(product.getStock() - quantity);
                productRepository.save(product);
                return ResponseEntity.ok().build();
            } else {
                throw new RuntimeException("Stock insuficiente");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/increaseStock")
    public ResponseEntity<Void> increaseStock(@PathVariable Long id, @RequestBody int quantity) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product producto = optionalProduct.get();
            producto.setStock(producto.getStock() + quantity);
            productRepository.save(producto);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
