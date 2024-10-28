package com.ism.data.repository.list;

import com.ism.core.Repository.RepositoryImpl;
import com.ism.data.entities.Client;
import com.ism.data.repository.interfaces.ClientRepositoryI;

public class ClientRepositoryList extends RepositoryImpl<Client> implements ClientRepositoryI {

    private int lastId = 0;

    @Override
    public boolean insert(Client object) {
        if (object == null) {
            return false; 
        }
        object.setId(++lastId);
        super.insert(object);
        return true;
    }

    @Override
    public Client selectByNumero(String numero) {
        if (numero == null) {
            return null; 
        }
        return list.stream()
                   .filter(client -> client.getTelephone() != null && client.getTelephone().compareTo(numero) == 0)
                   .findFirst()
                   .orElse(null);
    }
}
