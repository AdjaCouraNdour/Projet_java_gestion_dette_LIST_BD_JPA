package com.ism.data.repository.interfaces;

import java.util.List;
import com.ism.core.Repository.Repository;
import com.ism.data.entities.User;
import com.ism.data.enums.UserRole;

public interface UserRepositoryI extends Repository<User> {
    
    User selectById(int id) ;
    // User selectBy(UserRole role) ;
    List<User> selectByRole(UserRole role);
    User selectByLogin(String login) ;
    User selectByUserEtat(boolean etat) ;
    User selectByEmail(String email);
    

}
