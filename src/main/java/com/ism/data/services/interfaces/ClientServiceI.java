package com.ism.data.services.interfaces;

import com.ism.core.Services.Service;
import com.ism.data.entities.Client;

public interface ClientServiceI extends Service<Client> {
    Client getByNumero(String numero) ;
    // Client getById(int id) ;
    // boolean getBynumero(String tel);

}
