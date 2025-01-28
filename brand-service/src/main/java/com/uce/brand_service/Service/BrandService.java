package com.uce.brand_service.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uce.brand_service.Model.Brand;
import com.uce.brand_service.Repository.BrandRepository;
import com.uce.brand_service.Service.ServiceImp.BrandServiceImp;

@Service
public class BrandService implements BrandServiceImp {

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public Brand createBrand(Brand brand) {
        
        return brandRepository.save(brand);
    }

    @Override
    public List<Brand> getAll() {
        return brandRepository.findAll();
        
    }

    @Override
    public Brand findById(Long id) {
       return brandRepository.findById(id).orElse(null);
    }
    

}
