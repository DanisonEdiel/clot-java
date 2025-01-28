package com.uce.gender.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.uce.gender.Model.Gender;
import com.uce.gender.Repository.GenderRepository;
import com.uce.gender.Service.ServiceImp.GenderServiceImp;

@Service
public class GenderService implements GenderServiceImp {

    @Autowired
    private GenderRepository genderRepository;

    @Override
    public Gender savedGender(Gender gender) {
        if (genderRepository.existsByName(gender.getName())) {
            throw new DataIntegrityViolationException("This gender already exists");
        }

        return genderRepository.save(gender);
    }

    @Override
    public List<Gender> getAll() {
        return genderRepository.findAll();
    }

    @Override
    public Gender findById(Long id) {
        return genderRepository.findById(id).orElse(null);
    }

}
