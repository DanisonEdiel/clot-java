package com.uce.product.Service.ServiceImp;

import java.util.List;

import com.uce.product.Model.Product;
import com.uce.product.Model.Dto.ProductDto;

public interface ProductServiceImp {
    List<Product> getAllProduct(Long idCategory, Long idBrand);

    List<ProductDto> getAllProducts();

    boolean createProduct(Long idCategory, Long idBrand, Product product);

    Product findById(Long idCategory, Long idBrand, Long id);

    Product saveProducts (Product product);

    Product getProduct (Long id);

   
}
