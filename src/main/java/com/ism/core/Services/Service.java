package com.ism.core.Services;

import java.util.List;

public interface Service<T>{
    boolean save(T object);
    List<T> show();
    // boolean mettreAJour(T object);
    void effacer(T object);
     T getById(int id);
 
}
