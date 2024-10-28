package com.ism.core.Repository;

import java.util.ArrayList;
import java.util.List;

import com.ism.data.entities.Identifiable;

public class RepositoryImpl<T> implements Repository<T> {

    protected List<T> list = new ArrayList<>();
    
    @Override
    public boolean insert(T object) {
        return list.add(object);
    }

    @Override
    public List<T> selectAll() {
        return list;
    }

    @Override
    public void remove(T object) {
        list.remove(object);
    }

    @Override
    public T selectById(int id) {
        for (T object : list) {
            if (object instanceof Identifiable && ((Identifiable) object).getId() == id) {
                return object; 
            }
        }
        return null; 
    }
}
