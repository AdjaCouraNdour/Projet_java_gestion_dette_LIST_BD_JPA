package com.ism.data.services.list;

import java.util.List;

import com.ism.data.entities.User;
import com.ism.data.enums.UserRole;
import com.ism.data.repository.interfaces.UserRepositoryI;
import com.ism.data.services.interfaces.UserServiceImpl;

public class UserService implements UserServiceImpl {
    
    UserRepositoryI repo;

    public UserService(UserRepositoryI repo) {
        this.repo = repo;
    }

    @Override
    public boolean save(User object) {
        return repo.insert(object);
    }

    @Override
    public List<User> show() {
        return repo.selectAll();
    }

    @Override
    public User getById(int id) {
        return repo.selectById(id);
        // repo.selectAll().stream()
        //     .filter(user -> user.getId()==id)
        //     .findFirst()
        //     .orElse(null);  
    }

    @Override
    public User getByLogin(String login) {
        return repo.selectByLogin(login);

        // repo.selectAll().stream()
        //     .filter(user -> user.getLogin().compareTo(login)==0)
        //     .findFirst()
        //     .orElse(null);
    }


    public List<User> getByUserEtat() {
        return 
        repo.selectAll().stream()
            .filter(user -> user.isActif() == true)
            .toList();
    }

    @Override
    public User getByEmail(String email) {
        return repo.selectByEmail(email);
    }
        // repo.selectAll().stream()
        //     .filter(user -> user.getEmail().compareTo(email)==0)
        //     .findFirst()
        //     .orElse(null);
    // }

    @Override
    public List<User> getByUserRole(UserRole role) {
        return 
        repo.selectAll().stream()
            .filter(user -> user.getUserRole() == role)
            .toList();
    }

    @Override
    public void effacer(User object) {
        repo.remove(object);
    }


} 


   
    
