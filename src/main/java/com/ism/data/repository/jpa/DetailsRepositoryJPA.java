package com.ism.data.repository.jpa;

import com.ism.core.Repository.RepositoryJPA;
import com.ism.data.entities.Details;
import com.ism.data.repository.interfaces.DetailsRepositoryI;


public class DetailsRepositoryJPA extends RepositoryJPA<Details> implements DetailsRepositoryI {
     
    public DetailsRepositoryJPA( Class<Details> type) {
        super(type);
    }

}
