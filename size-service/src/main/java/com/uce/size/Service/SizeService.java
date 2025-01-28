package com.uce.size.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.uce.size.Model.Size;
import com.uce.size.Repository.SizeRepository;
import com.uce.size.Service.ServiceImp.SizeServiceImp;

@Service
public class SizeService implements SizeServiceImp {

    @Autowired
    private SizeRepository sizeRepository;

    @Override
    public Size savedSize(Size size) {
        if (sizeRepository.existsByName(size.getName())) {
            throw new DataIntegrityViolationException("This size already exists");
        }

        return sizeRepository.save(size);
    }

    @Override
    public List<Size> getAll() {
        return sizeRepository.findAll();
    }

    @Override
    public Size findById(Long id) {
        return sizeRepository.findById(id).orElse(null);
    }

}
