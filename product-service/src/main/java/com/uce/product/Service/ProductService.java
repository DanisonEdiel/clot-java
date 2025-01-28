package com.uce.product.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.uce.product.Model.Product;
import com.uce.product.Model.Dto.BrandDto;
import com.uce.product.Model.Dto.CategoryDto;
import com.uce.product.Model.Dto.ColorDto;
import com.uce.product.Model.Dto.GenderDto;
import com.uce.product.Model.Dto.ProductDto;
import com.uce.product.Model.Dto.SeasonDto;
import com.uce.product.Model.Dto.SizeDto;
import com.uce.product.Repository.ProductRepository;
import com.uce.product.Service.ServiceImp.ProductServiceImp;

@Service
public class ProductService implements ProductServiceImp {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${category.service.url}")
    private String categoryServiceUrl;

    @Value("${brand.service.url}")
    private String brandServiceUrl;

    @Value("${color.service.url}")
    private String colorServiceUrl;

    @Value("${season.service.url}")
    private String seasonServiceUrl;

    @Value("${size.service.url}")
    private String sizeServiceUrl;

    @Value("${gender.service.url}")
    private String genderServiceUrl;

    @Override
    public List<Product> getAllProduct(Long idCategory, Long idBrand) {
        List<Product> products = productRepository.findByIdCategoryAndIdBrand(idCategory, idBrand);
        return products;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::converToDTO)
                .collect(Collectors.toList());
    }

    private ProductDto converToDTO(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setProduct(product);
        CategoryDto category = restTemplate.getForObject(categoryServiceUrl + product.getIdCategory(),
                CategoryDto.class);
        productDto.setCategory(category);

        BrandDto brand = restTemplate.getForObject(
                brandServiceUrl + product.getIdBrand(),
                BrandDto.class);
        productDto.setBrand(brand);

        ColorDto color = restTemplate.getForObject(
                colorServiceUrl + product.getIdColor(),
                ColorDto.class);
        productDto.setColor(color);

        GenderDto gender = restTemplate.getForObject(
                genderServiceUrl + product.getIdGender(),
                GenderDto.class);
        productDto.setGender(gender);

        SeasonDto season = restTemplate.getForObject(
                seasonServiceUrl + product.getIdSeason(),
                SeasonDto.class);
        productDto.setSeason(season);

        SizeDto size = restTemplate.getForObject(
                sizeServiceUrl + product.getIdSize(),
                SizeDto.class);
        productDto.setSize(size);

        return productDto;
    }

    @Override
    public Product findById(Long idCategory, Long idBrand, Long id) {
        List<Product> products = productRepository.findByIdCategoryAndIdBrand(idCategory, idBrand);
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean createProduct(Long idCategory, Long idBrand, Product product) {
        if (idCategory != null && idBrand != null) {
            product.setIdCategory(idCategory);
            product.setIdBrand(idBrand);
            productRepository.save(product);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Product saveProducts(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

}
