package com.ism.data.repository.interfaces;

import java.util.List;

import com.ism.core.Repository.Repository;
import com.ism.data.entities.Dette;
import com.ism.data.enums.EtatDette;
import com.ism.data.enums.TypeDette;

public interface DetteRepositoryI extends Repository<Dette> {

     Dette selectById(int id) ;
     Dette selectBy(TypeDette type) ;
     List<Dette> selectByType(TypeDette type);
     List<Dette> selectByEtat(EtatDette etat);
     boolean update(Dette dette);
}
