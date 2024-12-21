package com.ism.data.repository.list;

import java.util.List;

import com.ism.core.Repository.RepositoryImpl;
import com.ism.data.entities.Details;
import com.ism.data.repository.interfaces.DetailsRepositoryI;

public class DetailsRepositoryList extends RepositoryImpl<Details> implements DetailsRepositoryI {
    
    private int lastId = 0;
    public boolean insert(Details object) {
        object.setId(++lastId);
        super.insert(object);
        return true;
    }
    @Override
    public List<Details> selectByDette(int detteId) {
        if (detteId <= 0) {
            System.out.println("ID de dette invalide : " + detteId);
            return List.of();
        }
        return list.stream()
                   .filter(detail -> detail.getDette() != null && detail.getDette().getId() == detteId)
                   .toList();
    }
    
    
    
}
