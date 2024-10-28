package com.ism.data.repository.list;

import com.ism.core.Repository.RepositoryImpl;
import com.ism.data.entities.Paiement;
import com.ism.data.repository.interfaces.PaiementRepositoryI;

public class PaiementRepositoryList extends RepositoryImpl<Paiement>  implements PaiementRepositoryI{
 private int lastId = 0;
    public boolean insert(Paiement object) {
        object.setId(++lastId);
        super.insert(object);
        return true;
    }
}
