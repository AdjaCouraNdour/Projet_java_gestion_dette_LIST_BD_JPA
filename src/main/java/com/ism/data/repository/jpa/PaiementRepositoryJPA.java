package com.ism.data.repository.jpa;

import com.ism.core.Repository.RepositoryJPA;
import com.ism.data.entities.Paiement;
import com.ism.data.repository.interfaces.PaiementRepositoryI;

import jakarta.persistence.EntityManager;

public class PaiementRepositoryJPA  extends RepositoryJPA<Paiement> implements PaiementRepositoryI {

    public PaiementRepositoryJPA(EntityManager em, Class<Paiement> type ) {
        super(em, type);
        this.tableName = "\"article\"";
    }

    @Override
    public Paiement selectById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectById'");
    }
    
}
