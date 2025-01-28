package com.uce.brand_service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uce.brand_service.Model.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    
}
