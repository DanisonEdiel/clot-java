package com.uce.category.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uce.category.Model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    
}
