package com.ism.data.repository.jpa;

import java.util.List;

import com.ism.core.Repository.RepositoryJPA;
import com.ism.data.entities.Article;
import com.ism.data.entities.Details;
import com.ism.data.entities.Dette;
import com.ism.data.repository.interfaces.DetailsRepositoryI;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;


public class DetailsRepositoryJPA extends RepositoryJPA<Details> implements DetailsRepositoryI {
     
    public DetailsRepositoryJPA( Class<Details> type) {
        super(type);
    }

    @Override
    public List<Details> selectByDette(int detteId) {
        try {
            TypedQuery<Details> query = em.createQuery(
                "SELECT d FROM Details d WHERE d.dette.id = :dette_id", Details.class);
            query.setParameter("dette_id", detteId);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
