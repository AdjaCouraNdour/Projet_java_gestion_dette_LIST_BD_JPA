package com.ism.data.repository.interfaces;

import com.ism.core.Repository.Repository;
import com.ism.data.entities.Paiement;

public interface PaiementRepositoryI extends Repository<Paiement>{
    
    Paiement selectById(int id) ;

}
