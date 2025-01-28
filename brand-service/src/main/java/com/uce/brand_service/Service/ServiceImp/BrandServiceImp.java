package com.uce.brand_service.Service.ServiceImp;

import java.util.List;

import com.uce.brand_service.Model.Brand;

public interface BrandServiceImp {

    Brand createBrand (Brand brand);
    List<Brand> getAll();
    Brand findById(Long id);
}
