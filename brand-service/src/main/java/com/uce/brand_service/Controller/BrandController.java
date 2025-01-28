package com.uce.brand_service.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uce.brand_service.Model.Brand;
import com.uce.brand_service.Service.BrandService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/brands")
@Tag(name = "Api Rest for brands use Swagger 3 - Brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping
    @Operation(summary = "This endpoint use for list all brands")
    public ResponseEntity<List<Brand>> getAll() {
        return ResponseEntity.ok(brandService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "This endpoint use for find by id brand")
    private ResponseEntity<Brand> getBrand(@PathVariable Long id) {
        Brand brand =brandService.findById(id);
        if (brand != null) {
            return new ResponseEntity<>(brand, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @Operation(summary = "This endpoint use for created a brands")
    private ResponseEntity<Brand> createBrand(@RequestBody Brand brand) {
        return ResponseEntity.status(HttpStatus.CREATED).body(brandService.createBrand(brand));
    }
}
