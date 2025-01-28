package com.uce.color.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.uce.color.Model.Color;
import com.uce.color.Repository.ColorRepository;
import com.uce.color.Service.ServiceImp.ColorServiceImp;

@Service
public class ColorService implements ColorServiceImp {

    @Autowired
    private ColorRepository colorRepository;

    @Override
    public Color savedColor(Color color) {
        if (colorRepository.existsByName(color.getName())) {
            throw new DataIntegrityViolationException("This color already exists");
        }

        return colorRepository.save(color);
    }

    @Override
    public List<Color> getAll() {
       return colorRepository.findAll();
    }

    @Override
    public Color findById(Long id) {
        return colorRepository.findById(id).orElse(null);
    }

}
