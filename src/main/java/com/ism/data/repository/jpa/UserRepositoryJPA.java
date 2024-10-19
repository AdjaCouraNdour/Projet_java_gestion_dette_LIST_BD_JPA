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

    public UserRepositoryJPA(EntityManager em, Class<User> type, ClientRepositoryI clientRepository) {
        super(em, type);
        this.clientRepository = clientRepository;
        this.tableName = "\"user\""; 
    }

    @Override
    public User selectById(int id) {
        User user = null; 
        try {
            user = em.find(User.class, id); 
            if (user == null) {
                System.out.println("Aucun utilisateur trouvé avec l'ID : " + id);
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la recherche de l'utilisateur par ID : " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) {
                em.close(); 
            }
        }
        return user; 
    }

    // @Override
    // public User selectBy(UserRole role) {
    //     User user = null;
    //     try {
    //         TypedQuery<User> query = em.createQuery(
    //             String.format("SELECT u FROM %s u WHERE u.userRole = :role", this.tableName),
    //             User.class);
    //         query.setParameter("role", role);
    //         user = query.getSingleResult();
    //     } catch (Exception e) {
    //         System.out.println("Erreur lors de la recherche de l'utilisateur par rôle : " + e.getMessage());
    //     } finally {
    //         if (em != null && em.isOpen()) {
    //             em.close(); 
    //         }
    //     }
    //     return user;
    // }

    @Override
    public User selectByLogin(String login) {
        User user = null;
        try {
            TypedQuery<User> query = em.createQuery(
                String.format("SELECT u FROM %s u WHERE u.login = :login", this.tableName),
                User.class);
            query.setParameter("login", login);
            user = query.getSingleResult();
        } catch (Exception e) {
            System.out.println("Erreur lors de la recherche de l'utilisateur par login : " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) {
                em.close(); 
            }
        }
        return user;
    }

    @Override
    public User selectByUserEtat(boolean etat) {
        User user = null;
        try {
            TypedQuery<User> query = em.createQuery(
                String.format("SELECT u FROM %s u WHERE u.etat = :etat", this.tableName),
                User.class);
            query.setParameter("etat", etat);
            user = query.getSingleResult();
        } catch (Exception e) {
            System.out.println("Erreur lors de la recherche de l'utilisateur par état : " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) {
                em.close(); 
            }
        }
        return user;
    }

    @Override
    public List<User> selectByRole(UserRole role) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectByRole'");
    }

    @Override
    public User selectByEmail(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectByEmail'");
    }
}
