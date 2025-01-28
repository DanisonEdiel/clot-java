package com.uce.size.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uce.size.Model.Size;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {
    boolean existsByName(String name);
}
