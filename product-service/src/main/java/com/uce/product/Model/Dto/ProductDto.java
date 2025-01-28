package com.uce.product.Model.Dto;

import com.uce.product.Model.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Product product;
    private CategoryDto category;
    private BrandDto brand;
    private ColorDto color;
    private SizeDto size;
    private GenderDto gender;
    private SeasonDto season;
}
