package com.ism.data.repository.jpa;

import java.util.List;
import com.ism.core.Repository.RepositoryJPA;
import com.ism.data.entities.User;
import com.ism.data.enums.UserRole;
import com.ism.data.repository.interfaces.ClientRepositoryI;
import com.ism.data.repository.interfaces.UserRepositoryI;
import jakarta.persistence.*;

public class UserRepositoryJPA extends RepositoryJPA<User> implements UserRepositoryI {
    
    ClientRepositoryI clientRepository;
    public UserRepositoryJPA(Class<User> type, ClientRepositoryI clientRepository) {
        super(type);
        this.clientRepository = clientRepository;
    }

    @Override
    public boolean insert(User object) {
        try {
            if (object.getClient() != null) {
                clientRepository.insert(object.getClient());
                object.setClient(clientRepository.selectById(object.getClient().getId()));
                super.insert(object); 
            } else {
                em.merge(object); 
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();  
            return false;
        }
    }

    @Override
    public User selectById(int id) {
        try {
            return em.find(User.class, id);
        } catch (Exception e) {
            System.out.println("Erreur lors de la recherche de l'utilisateur par ID : " + e.getMessage());
            return null;
        }
    }

    @Override
    public User selectByLogin(String login) {
        try {
            TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u WHERE u.login = :login", User.class);
            // TypedQuery<User> query = em.createNamedQuery("User.findByLogin", User.class);
            query.setParameter("login", login);
            return query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Aucun utilisateur trouvé avec le login : " + login);
            return null;
        } catch (Exception e) {
            System.out.println("Erreur lors de la recherche de l'utilisateur par login : " + e.getMessage());
            return null;
        }
    }

    @Override
    public User selectByUserEtat(boolean etat) {
        try {
            TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u WHERE u.actif = :etat", User.class);
            query.setParameter("etat", etat);
            return query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Aucun utilisateur trouvé avec l'état : " + etat);
            return null;
        } catch (Exception e) {
            System.out.println("Erreur lors de la recherche de l'utilisateur par état : " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<User> selectByRole(UserRole role) {
        try {
            TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u WHERE u.userRole = :role", User.class);
            query.setParameter("role", role);
            return query.getResultList();
        } catch (Exception e) {
            System.out.println("Erreur lors de la recherche des utilisateurs par rôle : " + e.getMessage());
            return null;
        }
    }

    @Override
    public User selectByEmail(String email) {
        try {
            TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u WHERE u.email = :email", User.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Aucun utilisateur trouvé avec l'email : " + email);
            return null;
        } catch (Exception e) {
            System.out.println("Erreur lors de la recherche de l'utilisateur par email : " + e.getMessage());
            return null;
        }
    }
}
