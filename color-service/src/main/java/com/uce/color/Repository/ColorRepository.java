package com.uce.color.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uce.color.Model.Color;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {
    boolean existsByName(String name);
}
