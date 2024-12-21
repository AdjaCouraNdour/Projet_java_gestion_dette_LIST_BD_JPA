package com.ism.data.enums;

public enum UserRole {
    Boutiquier,Admin,Client;

    public static UserRole getUserRole(String value) {
        for (UserRole userRole : UserRole.values()) {
            if (userRole.name().compareToIgnoreCase(value) == 0) {
                return userRole;
            }
        }
        return null;
    }

    public static UserRole getUserRoleId (int id) {
        for (UserRole userRole : UserRole.values()) {
            if (userRole.ordinal() == (id - 1)) {
                return userRole;
            }
        }
        return null;
    }

    public static int getUserRoleIdAsInt(UserRole role) {
        if (role != null) {
            return role.ordinal() + 1; 
        } else {
            throw new IllegalArgumentException("UserRole cannot be null");
        }
    }
    
}
