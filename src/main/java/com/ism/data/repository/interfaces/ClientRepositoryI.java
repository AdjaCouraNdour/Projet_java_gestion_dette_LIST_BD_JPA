package com.ism.data.repository.interfaces;

import com.ism.core.Repository.Repository;
import com.ism.data.entities.Client;


public interface ClientRepositoryI extends Repository<Client>  {

    Client selectById(int id) ;
    Client selectByNumero(String numero) ;

}
