package com.uce.color.Service.ServiceImp;

import java.util.List;

import com.uce.color.Model.Color;

public interface ColorServiceImp {
    Color savedColor(Color color);

    List<Color> getAll();

    Color findById(Long id);
}
