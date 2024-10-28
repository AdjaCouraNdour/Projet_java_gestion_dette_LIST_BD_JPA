package com.ism.data.repository.list;

import com.ism.core.Repository.RepositoryImpl;
import com.ism.data.entities.Details;

public class DetailsRepositoryList extends RepositoryImpl<Details>{
    
    private int lastId = 0;
    public boolean insert(Details object) {
        object.setId(++lastId);
        super.insert(object);
        return true;
    }
    
}
