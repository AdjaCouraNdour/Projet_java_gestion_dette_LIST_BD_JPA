package com.ism.data.services.interfaces;

import java.util.List;

import com.ism.core.Services.Service;
import com.ism.data.entities.User;
import com.ism.data.enums.UserRole;

public interface UserServiceImpl extends Service<User> {
     // User getById(int id) ;
     // User getBy(UserRole role) ;
     User getByLogin(String login) ;
     User getByEmail(String email);
     List<User> getByUserEtat();
     List<User> getByUserRole(UserRole role);
}
