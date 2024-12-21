package com.ism.data.services.interfaces;

import java.util.List;

import com.ism.core.Services.Service;
import com.ism.data.entities.Dette;
import com.ism.data.enums.EtatDette;
import com.ism.data.enums.TypeDette;

public interface DetteServiceI extends Service<Dette> {
    // Dette getById(int id) ;
    Dette getBy(TypeDette etat);
    List<Dette> getByType(TypeDette type);
    List<Dette> getByEtat(EtatDette etat);

}
