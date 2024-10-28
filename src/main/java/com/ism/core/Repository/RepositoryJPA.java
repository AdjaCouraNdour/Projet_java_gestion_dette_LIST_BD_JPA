package com.ism.core.Repository;

import java.util.*;
import com.ism.core.Services.YamlService;
import com.ism.core.Services.YamlServiceImpl;
import jakarta.persistence.*;

public class RepositoryJPA<T> implements Repository<T> {
    
    protected EntityManager em;
    protected EntityManagerFactory emf ;
    protected Class<T> type;
    protected String tableName;
    YamlService yamlService ;

    public RepositoryJPA( Class<T> type) {
        this.type = type;
        
        if (em == null) {
            yamlService = new YamlServiceImpl();
            Map<String, Object> mapYaml = yamlService.loadYaml();
            this.emf = Persistence.createEntityManagerFactory(mapYaml.get("persistence").toString()); 
            this.em = emf.createEntityManager(); 
        } else {
            this.em = em; 
        }
    }

    @Override
    public boolean insert(T object) {
        boolean success = false;
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            if (em.contains(object)) {
                em.merge(object); // L'entité est déjà attachée, donc on utilise merge
            } else {
                em.persist(object); // L'entité est nouvelle, donc on la persiste
            }
            // em.persist(object); 
            transaction.commit();
            success = true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
        return success;
    }

    @Override
    public List<T> selectAll() {
        String entityName = type.getSimpleName();  
        return this.em.createQuery("SELECT u FROM " + entityName + " u", type).getResultList();
    }


    @Override
    public T selectById(int id) {
        T entity = null;
        try {
            entity = em.find(type, id);  
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity; 
    }

    
    public void close() {
        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    @Override
    public void remove(T object) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            T mergedObject = em.merge(object);  
            em.remove(mergedObject);  
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
