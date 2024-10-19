package com.ism.data.repository.jpa;

import com.ism.core.Repository.RepositoryJPA;
import com.ism.data.entities.Details;
import com.ism.data.repository.interfaces.DetailsRepositoryI;

import jakarta.persistence.EntityManager;

public class DetailsRepositoryJPA extends RepositoryJPA<Details> implements DetailsRepositoryI {
     
    public DetailsRepositoryJPA(EntityManager em, Class<Details> type) {
        super(em, type);
        this.tableName = "\"details\""; 
    }

}
