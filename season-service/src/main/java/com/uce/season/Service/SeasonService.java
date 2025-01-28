package com.uce.season.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.uce.season.Model.Season;
import com.uce.season.Repository.SeasonRepository;
import com.uce.season.Service.ServiceImp.SeasonServiceImp;

@Service
public class SeasonService implements SeasonServiceImp {

    @Autowired
    private SeasonRepository seasonRepository;

    @Override
    public Season savedSeason(Season season) {
        if (seasonRepository.existsByName(season.getName())) {
            throw new DataIntegrityViolationException("This season already exists");
        }

        return seasonRepository.save(season);
    }

    @Override
    public List<Season> getAll() {
        return seasonRepository.findAll();
    }

    @Override
    public Season findById(Long id) {
        return seasonRepository.findById(id).orElse(null);
    }

}
