package com.ism.data.services.interfaces;

import java.util.List;

import com.ism.core.Services.Service;
import com.ism.data.entities.Details;
import com.ism.data.entities.Dette;

public interface DetailsServiceI extends Service<Details> {
    List<Details>  getByDette(int detteId);

}
