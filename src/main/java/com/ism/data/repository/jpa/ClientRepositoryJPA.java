package com.ism.data.repository.jpa;

import com.ism.core.Repository.RepositoryJPA;
import com.ism.data.entities.Client;
import com.ism.data.repository.interfaces.ClientRepositoryI;
import com.ism.data.repository.interfaces.UserRepositoryI;
import jakarta.persistence.*;

public class ClientRepositoryJPA extends RepositoryJPA<Client> implements ClientRepositoryI {

    UserRepositoryI userRepository;

    public ClientRepositoryJPA(EntityManager em, Class<Client> type, UserRepositoryI userRepository) {
        super(em, type);
        this.userRepository = userRepository;
        this.tableName = "\"client\""; // Proper quoting for PostgreSQL if table name is lowercase
    }

    @Override
    public Client selectById(int id) {
        Client client = null;
        try {
            client = em.find(Client.class, id); // Recherche par ID
            if (client == null) {
                System.out.println("Aucun client trouvé avec l'ID : " + id);
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la recherche du client par ID : " + e.getMessage());
        }
        return client;
    }

    @Override
    public Client selectByNumero(String numero) {
        Client client = null;
        try {
            TypedQuery<Client> query = em.createQuery(
                String.format("SELECT c FROM %s c WHERE c.telephone = :numero", this.tableName),
                Client.class);
            query.setParameter("numero", numero); // Correspondance correcte du nom du paramètre
            client = query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Aucun client trouvé avec le numéro : " + numero);
        } catch (Exception e) {
            System.out.println("Erreur lors de la recherche du client par numéro : " + e.getMessage());
        }
        return client;
    }

 
}
