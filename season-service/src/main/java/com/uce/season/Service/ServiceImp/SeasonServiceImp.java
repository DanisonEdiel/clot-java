package com.uce.season.Service.ServiceImp;

import java.util.List;

import com.uce.season.Model.Season;

public interface SeasonServiceImp {
    Season savedSeason(Season season);

    List<Season> getAll();

    Season findById(Long id);
}
