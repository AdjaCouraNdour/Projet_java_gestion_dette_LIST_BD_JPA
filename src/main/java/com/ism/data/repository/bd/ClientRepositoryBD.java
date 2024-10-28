package com.ism.data.repository.bd;

import java.sql.*;

import com.ism.core.Repository.RepositoryBDImpl;
import com.ism.data.entities.Client;
import com.ism.data.entities.Dette;
import com.ism.data.enums.TypeDette;
import com.ism.data.repository.interfaces.ClientRepositoryI;
import com.ism.data.repository.interfaces.UserRepositoryI;

public class ClientRepositoryBD extends RepositoryBDImpl<Client> implements ClientRepositoryI {
    
    private UserRepositoryI userRepository;

    public ClientRepositoryBD() {
        this.tableName = "client";
    }

    public void setUserRepository(UserRepositoryI userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected String generateInsertQuery() {
        return String.format("INSERT INTO %s (prenom, nom, telephone, address, user_id) VALUES (?, ?, ?, ?, ?)", this.tableName);
    }

    @Override
    protected String getTableName() {  
        return this.tableName;
    }

    @Override
    protected void setIdFromResultSet(Client object, ResultSet rs) throws SQLException {
        object.setId(rs.getInt(1)); 
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, Client object) throws SQLException {
        ps.setString(1, object.getPrenom());
        ps.setString(2, object.getNom());
        ps.setString(3, object.getTelephone());
        ps.setString(4, object.getAddress());
        if (object.getUser() != null) {
            ps.setInt(5, object.getUser().getId());
        } else {
            ps.setNull(5, java.sql.Types.INTEGER);
        }
    }

    @Override
        public boolean insert(Client client) {
             // Appeler la méthode générique d'insertion
            boolean ok = super.insert(client);
     
            if (ok) {
                try {
                    // insert la dette
                    if (!client.getListeDette().isEmpty()) {
                        insertDettes(conn ,client);
                    }
    
                 } catch (SQLException e) {
                     System.out.println("Erreur lors de l'insertion des dettes " + client.getId() + " - " + e.getMessage());
                     return false;
                 }      
             }
            return ok;
        }

    private void insertDettes(Connection conn,Client client) throws SQLException {
        String query = "INSERT INTO dette (montant, montant_verse, montant_restant, type_dette_id, archiver, client_id, date) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) { 
            for (Dette dette : client.getListeDette()) {
                ps.setDouble(1, dette.getMontant());
                ps.setDouble(2, dette.getMontantVerse());
                ps.setDouble(3, dette.getMontantRestant());
                ps.setInt(4, dette.getTypeDette().ordinal() + 1);
                ps.setBoolean(5, dette.isArchiver());
                ps.setInt(6, client.getId());
                if (dette.getDate() != null) {
                    ps.setDate(7, java.sql.Date.valueOf(dette.getDate()));
                } else {
                    ps.setNull(7, java.sql.Types.DATE);
                }
                ps.executeUpdate();
            } 
        }
    }

    @Override
    protected Client converToObject(ResultSet rs) throws SQLException {
        Client client = new Client();
        client.setId(rs.getInt("id")); 
        client.setPrenom(rs.getString("prenom")); 
        client.setNom(rs.getString("nom")); 
        client.setTelephone(rs.getString("telephone")); 
        client.setAddress(rs.getString("address"));

        int userId = rs.getInt("user_id");
        if (userId > 0) { 
            client.setUser(userRepository.selectById(userId));
        }

        // Récupérer la liste des dettes associées au client
        Dette dettes = getDettesByClientId(client.getId());
            if (dettes != null) {
                client.setListeDette(dettes);
            }
        // client.setListeDette(getDettesByClientId(client.getId()));

        return client;
    }

    private Dette getDettesByClientId(int clientId) {
        String query = "SELECT * FROM dette WHERE client_id = ?";
        try (Connection conn = connexion();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, clientId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Dette dette = new Dette();
                    dette.setId(rs.getInt("id"));
                    dette.setMontant(rs.getDouble("montant"));
                    dette.setMontantVerse(rs.getDouble("montant_verse"));
                    dette.setMontantRestant(rs.getDouble("montant_restant"));
                    dette.setTypeDette(TypeDette.getTypeDetteId(rs.getInt("type_dette_id"))); 
                    dette.setArchiver(rs.getBoolean("archiver"));
                    return dette;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des dettes: " + e.getMessage());
        }   
        return null;
    }

    @Override
    public Client selectByNumero(String numero) {
        Client client = null;
        String query = String.format("SELECT * FROM %s WHERE telephone = ?", this.tableName);
        try (Connection conn = connexion();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, numero);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    client = converToObject(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la requête: " + e.getMessage());
        }
        return client;
    }

    @Override
    public void remove(Client client) {
        String query = String.format("DELETE FROM %s WHERE id = ?", this.tableName);
        try (Connection conn = connexion();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, client.getId());
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Client supprimé avec succès");
            } else {
                System.out.println("Aucun client trouvé avec cet ID");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression: " + e.getMessage());
        }
    }
}