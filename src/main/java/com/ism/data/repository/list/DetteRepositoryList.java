package com.ism.data.repository.list;

import java.util.List;

import com.ism.core.Repository.RepositoryImpl;
import com.ism.data.entities.Dette;
import com.ism.data.enums.EtatDette;
import com.ism.data.enums.TypeDette;
import com.ism.data.repository.interfaces.DetteRepositoryI;

public class DetteRepositoryList extends RepositoryImpl<Dette> implements DetteRepositoryI {
    private int lastId = 0;

    @Override
    public boolean insert(Dette object) {
        if (object == null) {
            return false; 
        }
        object.setId(++lastId);
        super.insert(object);
        return true;
    }

    @Override
    public Dette selectBy(TypeDette type) {
        if (type == null) {
            return null;
        }
        return list.stream()
                   .filter(dette -> dette.getTypeDette() != null && dette.getTypeDette() == type)
                   .findFirst()
                   .orElse(null);
    }

    @Override
    public List<Dette> selectByType(TypeDette type) {
        if (type == null) {
            return List.of(); 
        }
        return list.stream()
                   .filter(dette -> dette.getTypeDette() != null && dette.getTypeDette() == type)
                   .toList();
    }

    @Override
    public boolean update(Dette dette) {
        if (dette == null || dette.getId() == 0) {
            return false; 
        }
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == dette.getId()) {
                list.set(i, dette);
                return true;
            }
        }
        return false; 
    }

    @Override
    public List<Dette> selectByEtat(EtatDette etat) {
        if (etat == null) {
            return List.of(); 
        }
        return list.stream()
                   .filter(dette -> dette.getEtatDette() != null && dette.getEtatDette() == etat)
                   .toList();
    }
}
