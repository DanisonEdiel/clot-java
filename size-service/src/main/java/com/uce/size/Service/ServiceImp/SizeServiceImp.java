package com.uce.size.Service.ServiceImp;

import java.util.List;

import com.uce.size.Model.Size;

public interface SizeServiceImp {
    Size savedSize(Size size);

    List<Size> getAll();

    Size findById(Long id);


}
