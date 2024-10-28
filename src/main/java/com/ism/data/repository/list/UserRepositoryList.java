package com.ism.data.repository.list;

import java.util.List;

import com.ism.core.Repository.RepositoryImpl;
import com.ism.data.entities.User;
import com.ism.data.enums.UserRole;
import com.ism.data.repository.interfaces.UserRepositoryI;

public class UserRepositoryList extends RepositoryImpl<User> implements UserRepositoryI {
    private int lastId = 0;
    public boolean insert(User object) {
        object.setId(++lastId);
        super.insert(object);
        return true;
    }

    public User selectBy(UserRole role) {
        if (role == null) {
            return null; 
        }
        return list.stream()
                   .filter(user -> user.getUserRole() != null && user.getUserRole() == role)
                   .findFirst()
                   .orElse(null);
    }

    @Override
    public User selectByLogin(String login) {
        if (login == null) {
            return null; // Vérification si le login est null
        }
        return list.stream()
                   .filter(user -> user.getLogin() != null && user.getLogin().compareTo(login) == 0)
                   .findFirst()
                   .orElse(null);
    }


    @Override
    public User selectByUserEtat(boolean etat) {
        return
        list.stream()
        .filter(user -> user.isActif()==etat)
        .findAny()
        .orElse(null);    
    }

    @Override
    public List<User> selectByRole(UserRole role) {
        if (role == null) {
            return List.of(); 
        }
        return list.stream()
                   .filter(user -> user.getUserRole() != null && user.getUserRole() == role)
                   .toList(); 
    }


    @Override
    public User selectByEmail(String email) {
        if (email == null) {
            return null; // Vérification si l'email est null
        }
        return list.stream()
                   .filter(user -> user.getEmail() != null && user.getEmail().compareTo(email) == 0)
                   .findFirst()
                   .orElse(null);
    }
  
}
