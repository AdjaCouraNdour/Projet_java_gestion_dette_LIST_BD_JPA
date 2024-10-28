package com.ism.data.repository.jpa;

import com.ism.core.Repository.RepositoryJPA;
import com.ism.data.entities.Paiement;
import com.ism.data.repository.interfaces.PaiementRepositoryI;


public class PaiementRepositoryJPA  extends RepositoryJPA<Paiement> implements PaiementRepositoryI {

    public PaiementRepositoryJPA( Class<Paiement> type ) {
        super( type);
    }

    @Override
    public Paiement selectById(int id) {
        Paiement paiement= new Paiement();
         try {
            paiement = em.find(Paiement.class, id);
        } catch (Exception e) {
            System.out.println("Erreur lors de la recherche du piament par ID : " + e.getMessage());
        }
        return paiement;
    }
    
    
}
