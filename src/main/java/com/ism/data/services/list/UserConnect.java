package com.ism.data.services.list;

import com.ism.data.entities.User;

public class UserConnect {
    
    public static User userConnect;

    public static User getUserConnect() {
        return userConnect;
    }

    public static void setUserConnect(User userConnect) {
        UserConnect.userConnect = userConnect;
    }

}

