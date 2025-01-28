package com.uce.gender.Service.ServiceImp;

import java.util.List;

import com.uce.gender.Model.Gender;

public interface GenderServiceImp {
    Gender savedGender(Gender gender);

    List<Gender> getAll();

    Gender findById(Long id);
}
