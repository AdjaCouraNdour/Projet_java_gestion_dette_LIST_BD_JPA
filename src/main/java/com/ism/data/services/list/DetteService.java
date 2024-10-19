package com.ism.data.services.list;

import java.util.List;

import com.ism.data.entities.Dette;
import com.ism.data.enums.TypeDette;
import com.ism.data.repository.interfaces.DetteRepositoryI;
import com.ism.data.services.interfaces.DetteServiceI;

public class DetteService implements DetteServiceI{

    DetteRepositoryI repo;

    public DetteService(DetteRepositoryI repo) {
        this.repo = repo;
    }

    @Override
    public Dette getById(int id) {
        return
        repo.selectAll().stream()
        .filter(client -> client.getId()==id)
        .findFirst()
        .orElse(null);  
    }

    @Override
    public Dette getBy(TypeDette etat) {
        return repo.selectBy(etat);
        // repo.selectAll().stream()
        // .filter(dette -> dette.getTypeDette().compareTo(etat)==0)
        // .findFirst()
        // .orElse(null);  
    }

    @Override
    public boolean save(Dette object) {
        return repo.insert(object);
    }

    @Override
    public List<Dette> show() {
        return repo.selectAll();
    }


    @Override
    public void effacer(Dette object) {
        repo.remove(object);
    }

    @Override
    public List<Dette> getByType(TypeDette type) {
       return repo.selectByType(type);
    }
    
}
