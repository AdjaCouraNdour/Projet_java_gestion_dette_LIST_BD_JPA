package com.ism.data.repository.list;

import com.ism.core.Repository.RepositoryImpl;
import com.ism.data.entities.Client;
import com.ism.data.repository.interfaces.ClientRepositoryI;

public class ClientRepositoryList extends RepositoryImpl<Client> implements ClientRepositoryI {

    @Override
    public Client selectByNumero(String numero) {
        return
        list.stream()
        .filter(client -> client.getTelephone().compareTo(numero)==0)
        .findFirst()
        .orElse(null);
    }
   
}
