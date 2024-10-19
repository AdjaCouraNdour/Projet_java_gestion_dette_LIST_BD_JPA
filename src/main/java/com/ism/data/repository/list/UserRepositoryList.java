package com.ism.data.repository.list;

import java.util.List;

import com.ism.core.Repository.RepositoryImpl;
import com.ism.data.entities.User;
import com.ism.data.enums.UserRole;
import com.ism.data.repository.interfaces.UserRepositoryI;

public class UserRepositoryList extends RepositoryImpl<User> implements UserRepositoryI {

    public User selectBy(UserRole role) {
        return
        list.stream()
        .filter(user -> user.getUserRole()==role)
        .findAny()
        .orElse(null);
    }

    @Override
    public User selectByLogin(String login) {
        return
        list.stream()
        .filter(user -> user.getLogin().compareTo(login)==0)
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
        return list.stream()
            .filter(user -> user.getUserRole() == role) 
            .toList(); 
    }

    @Override
    public User selectByEmail(String email) {
        return
        list.stream()
        .filter(user -> user.getEmail().compareTo(email)==0)
        .findFirst()
        .orElse(null);
    }
  
}
