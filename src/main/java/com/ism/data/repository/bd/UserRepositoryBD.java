package com.ism.data.repository.bd;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.ism.core.Repository.RepositoryBDImpl;
import com.ism.data.entities.Client;
import com.ism.data.entities.User;
import com.ism.data.enums.UserRole;
import com.ism.data.repository.interfaces.ClientRepositoryI;
import com.ism.data.repository.interfaces.UserRepositoryI;

public class UserRepositoryBD extends RepositoryBDImpl<User> implements UserRepositoryI {

    private ClientRepositoryI clientRepository;

    public UserRepositoryBD(ClientRepositoryI clientRepository) {
        this.clientRepository = clientRepository;
        this.tableName = "userr";
    }
 
    public void setClientRepository(ClientRepositoryI clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, User object) throws SQLException {
        ps.setString(1, object.getEmail());
        ps.setString(2, object.getLogin());
        ps.setString(3, object.getPassword());
        ps.setBoolean(4, object.isActif());
        ps.setInt(5, object.getUserRole().ordinal() + 1);

        if (object.getClient() != null) {
            ps.setInt(6, object.getClient().getId());
        } else {
            ps.setNull(6, Types.INTEGER);
        }
    }

    @Override
    protected String generateInsertQuery() {
        return String.format("INSERT INTO %s (email, login, password, actif, user_role_id, client_id) VALUES (?, ?, ?, ?, ?, ?);", this.tableName);
    }

    @Override
    protected User converToObject(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setEmail(rs.getString("email"));
        user.setLogin(rs.getString("login"));
        user.setPassword(rs.getString("password"));
        user.setActif(rs.getBoolean("actif"));
        user.setUserRole(UserRole.getUserRoleId(rs.getInt("user_role_id")));
        
        
        int clientId = rs.getInt("client_id");
        if (clientId > 0) {
            Client client = clientRepository.selectById(clientId);
            user.setClient(client);
        }

        return user;
    }

    @Override
    protected void setIdFromResultSet(User object, ResultSet rs) throws SQLException {
        object.setId(rs.getInt(1));
    }

    @Override
    protected String getTableName() {
       return this.tableName ;
    }

    @Override
    public List<User> selectByRole(UserRole role) {
        List<User> users = new ArrayList<>();
        String query = String.format("SELECT * FROM %s WHERE user_role_id = ?", this.tableName);
        
        try (Connection conn = connexion();
             PreparedStatement ps = conn.prepareStatement(query)) {
             
            ps.setInt(1, UserRole.getUserRoleIdAsInt(role));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    users.add(converToObject(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'exécution de la requête : " + e.getMessage());
        }

        return users;
    }

    // @Override
    // public User selectLogin(String login) {
    //     return selectUser("login", login);
    // }

    @Override
    public User selectByUserEtat(boolean etat) {
        return selectUser("actif", String.valueOf(etat));
    }

    // @Override
    // public User selectByEmail(String email) {
    //     return selectUser("email", email);
    // }

    private User selectUser(String column, String value) {
        User user = null;
        String query = String.format("SELECT * FROM \"%s\" WHERE "+column+" = ?", this.tableName);
        try (Connection conn = connexion();
             PreparedStatement ps = conn.prepareStatement(query)) {
             
            ps.setString(1, value);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = converToObject(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur requête : " + e.getMessage());
        }

        return user;
    }
    @Override
        public User selectByEmail(String email) {
            User user = null;
            String query = String.format("SELECT * FROM \"%s\" WHERE email = ?", this.tableName);
            System.out.println("Requête exécutée : " + query);
            try (Connection conn = connexion();
                 PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, email);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        user = converToObject(rs);
                    }
                }
            } catch (SQLException e) {
                System.out.println("Erreur lors de la requête: " + e.getMessage());
            }
            return user;
        }
    @Override
        public User selectByLogin(String login) {
            User user = null;
            String query = String.format("SELECT * FROM \"%s\" WHERE email = ?", this.tableName);
            System.out.println("Requête exécutée : " + query);
            try (Connection conn = connexion();
                 PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, login);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        user = converToObject(rs);
                    }
                }
            } catch (SQLException e) {
                System.out.println("Erreur lors de la requête: " + e.getMessage());
            }
            return user;
        }
}

