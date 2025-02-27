package com.uce.gender.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uce.gender.Model.Gender;

@Repository
public interface GenderRepository extends JpaRepository<Gender, Long> {
    boolean existsByName(String name);
}
