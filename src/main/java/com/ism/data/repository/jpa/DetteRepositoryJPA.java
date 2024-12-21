package com.ism.data.repository.jpa;

import java.util.List;

import com.ism.core.Repository.RepositoryJPA;
import com.ism.data.entities.Dette;
import com.ism.data.enums.EtatDette;
import com.ism.data.enums.TypeDette;
import com.ism.data.repository.interfaces.DetteRepositoryI;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class DetteRepositoryJPA extends RepositoryJPA<Dette> implements DetteRepositoryI {

    public DetteRepositoryJPA(Class<Dette> type) {
        super( type);
    }

    @Override
    public Dette selectById(int id) {
        Dette dette = null;
        try {
            dette = em.find(Dette.class, id);
        } catch (Exception e) {
            System.out.println("Erreur lors de la recherche de la dette par ID : " + e.getMessage());
        }
        return dette;
    }

    @Override
    public List<Dette> selectByType(TypeDette type) {
        List<Dette> dettes = null;
        try {
            TypedQuery<Dette> query = em.createQuery(
                "SELECT d FROM Dette d WHERE d.typeDette = :type", Dette.class);
            query.setParameter("type", type);
            dettes = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erreur lors de la recherche de dettes par type : " + e.getMessage());
        }
        return dettes;
    }

    @Override
    public Dette selectBy(TypeDette type) {
        Dette dette = null;
        try {
            TypedQuery<Dette> query = em.createQuery(
                "SELECT d FROM Dette d WHERE d.typeDette = :type", Dette.class);
            query.setParameter("type", type);
            dette = query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Aucune dette trouvée avec le type : " + type);
        } catch (Exception e) {
            System.out.println("Erreur lors de la recherche de la dette par type : " + e.getMessage());
        }
        return dette;
    }


    @Override
    public boolean update(Dette dette) {
        try {
            if (dette != null) {
                Dette existingDette = em.find(Dette.class, dette.getId());
                if (existingDette != null) {
                    em.getTransaction().begin();  
                    em.merge(dette);               
                    em.getTransaction().commit();  
                    return true;
                } else {
                    System.out.println("Aucune dette trouvée avec l'ID : " + dette.getId());
                    return false;
                }
            }
        } catch (Exception e) {
            em.getTransaction().rollback();  
            System.out.println("Erreur lors de la mise à jour de la dette : " + e.getMessage());
            return false;
        }
        return false;
    }

    @Override
    public List<Dette> selectByEtat(EtatDette etat) {
        List<Dette> dettes = null;
        try {
            TypedQuery<Dette> query = em.createQuery(
                "SELECT d FROM Dette d WHERE d.etatDette = :etat", Dette.class);
            query.setParameter("etat", etat);
            dettes = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erreur lors de la recherche de dettes par etat : " + e.getMessage());
        }
        return dettes;
    }

}
