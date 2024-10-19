package com.ism.data.services.list;

import java.util.List;

import com.ism.data.entities.Paiement;
import com.ism.data.repository.interfaces.PaiementRepositoryI;
import com.ism.data.services.interfaces.PaiementServiceI;

public class PaiementService implements PaiementServiceI{

   PaiementRepositoryI repo;

    public PaiementService(PaiementRepositoryI repo) {
        this.repo = repo;
    }

    @Override
    public boolean save(Paiement object) {
        return repo.insert(object);
    }

    @Override
    public List<Paiement> show() {
        return repo.selectAll();
    }

    @Override
    public void effacer(Paiement object) {
        repo.remove(object);
    }

    @Override
    public Paiement getById(int id) {
        return repo.selectById(id);
        // repo.selectAll().stream()
        // .filter(paiement -> paiement.getId()==id)
        // .findFirst()
        // .orElse(null);
    } 
}
