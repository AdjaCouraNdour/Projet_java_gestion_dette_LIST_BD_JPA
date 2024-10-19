package com.ism.data.repository.jpa;

import java.util.List;

import com.ism.core.Repository.RepositoryJPA;
import com.ism.data.entities.Dette;
import com.ism.data.enums.TypeDette;
import com.ism.data.repository.interfaces.DetteRepositoryI;

import jakarta.persistence.EntityManager;

public class DetteRepositoryJPA extends RepositoryJPA<Dette> implements DetteRepositoryI {

     public DetteRepositoryJPA(EntityManager em, Class<Dette> type) {
        super(em, type);
        this.tableName = "\"details\""; 
    }
    
    @Override
    public Dette selectById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectById'");
    }

    @Override
    public Dette selectBy(TypeDette type) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectBy'");
    }

    @Override
    public List<Dette> selectByType(TypeDette type) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectByType'");
    }
    
}
