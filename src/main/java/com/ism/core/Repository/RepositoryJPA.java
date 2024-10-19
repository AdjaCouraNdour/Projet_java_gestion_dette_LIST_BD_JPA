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

    public RepositoryJPA(EntityManager em, Class<T> type) {
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
        try {
            em.getTransaction().begin();
            em.persist(object);  // Use persist() for new objects
            em.getTransaction().commit();
            success = true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            // Do not close the EntityManager here if managed externally (e.g., by a service layer)
        }
        return success;
    }

    @Override
    public List<T> selectAll() {
        String entityName = type.getSimpleName();  // Get entity name for dynamic query
        return this.em.createQuery("SELECT u FROM " + entityName + " u", type).getResultList();
    }

    @Override
    public void remove(T object) {
        try {
            em.getTransaction().begin();
            T mergedObject = em.merge(object);  // Merge the object if it's detached
            em.remove(mergedObject);  // Then remove it
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public T selectById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectById'");
    }

}
