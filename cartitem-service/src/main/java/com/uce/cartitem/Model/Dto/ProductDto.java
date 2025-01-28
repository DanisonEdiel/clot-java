package com.uce.cartitem.Model.Dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private int stock;
    private double price;
    private Long idCategory;
    private Long idBrand;
}
