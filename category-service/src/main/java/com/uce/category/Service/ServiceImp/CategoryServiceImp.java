package com.uce.category.Service.ServiceImp;

import java.util.List;

import com.uce.category.Model.Category;

public interface CategoryServiceImp {
    Category createCategory(Category category);

    List<Category> getAllCategories();

    Category findById(Long id);
}
