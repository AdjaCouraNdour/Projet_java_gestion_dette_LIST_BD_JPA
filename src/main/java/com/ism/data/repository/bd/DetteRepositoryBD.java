package com.ism.data.repository.bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ism.core.Repository.RepositoryBDImpl;
import com.ism.data.entities.Client;
import com.ism.data.entities.Details;
import com.ism.data.entities.Dette;
import com.ism.data.entities.Paiement;
import com.ism.data.enums.EtatDette;
import com.ism.data.enums.TypeDette;
import com.ism.data.repository.interfaces.ArticleRepositoryI;
import com.ism.data.repository.interfaces.ClientRepositoryI;
import com.ism.data.repository.interfaces.DetteRepositoryI;

public class DetteRepositoryBD extends RepositoryBDImpl<Dette> implements DetteRepositoryI {

    private final ClientRepositoryI clientRepository;
    private final ArticleRepositoryI articleRepository;

    public DetteRepositoryBD(ClientRepositoryI clientRepository, ArticleRepositoryI articleRepository) {
        this.clientRepository = clientRepository;
        this.articleRepository = articleRepository;
        this.tableName = "dette";
    }

    @Override
    public boolean insert(Dette dette) {
        boolean inserted = super.insert(dette);

        if (inserted) {
            try (Connection conn = connexion()) {
                insertDetails(conn, dette);
                if (!dette.getListePaiements().isEmpty()) {
                    insertPaiements(conn, dette);
                }
                                 
            } catch (SQLException e) {
                System.out.println("Erreur lors de l'insertion des paiements ou des détails pour la dette ID: " + dette.getId() + " - " + e.getMessage());
                return false;
            }
        }
        return inserted;
    }

    @Override
    protected String generateInsertQuery() {
        return String.format("INSERT INTO %s (montant, montant_verse, montant_restant, type_dette_id, archiver, client_id, date , etat_dette_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", this.tableName);
    }

    @Override
    protected void setIdFromResultSet(Dette object, ResultSet rs) throws SQLException {
        object.setId(rs.getInt(1));
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, Dette dette) throws SQLException {
        ps.setDouble(1, dette.getMontant());
        ps.setDouble(2, dette.getMontantVerse());
        ps.setDouble(3, dette.getMontantRestant());
        ps.setInt(4, dette.getTypeDette().ordinal() + 1);
        ps.setBoolean(5, dette.isArchiver());
        if (dette.getClient() != null) {
            ps.setInt(6, dette.getClient().getId());
        } else {
            ps.setNull(6, java.sql.Types.INTEGER);
        }
        if (dette.getDate() != null) {
            ps.setDate(7, java.sql.Date.valueOf(dette.getDate()));
        } else {
            ps.setNull(7, java.sql.Types.DATE);
        }
        ps.setInt(8, dette.getEtatDette().ordinal() + 1);

    }

    private void insertDetails(Connection conn, Dette dette) throws SQLException {
        String queryDetails = "INSERT INTO details (qte_dette, dette_id, article_id) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(queryDetails)) {
            for (Details detail : dette.getListeDetails()) {
                ps.setDouble(1, detail.getQteDette());
                ps.setInt(2, dette.getId());
                ps.setInt(3, detail.getArticle().getId());
                ps.executeUpdate();
            }
        }
    }

    private void insertPaiements(Connection conn, Dette dette) throws SQLException {
        String queryPaiements = "INSERT INTO paiements (montant, date, dette_id) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(queryPaiements)) {
            for (Paiement paiement : dette.getListePaiements()) {
                ps.setDouble(1, paiement.getMontant());
                ps.setDate(2, java.sql.Date.valueOf(paiement.getDate()));
                ps.setInt(3, dette.getId());
                ps.executeUpdate();
            }
        }
    }

    @Override
    protected Dette converToObject(ResultSet rs) throws SQLException {
        Dette dette = new Dette();
        dette.setId(rs.getInt("id"));
        dette.setMontant(rs.getDouble("montant"));
        dette.setMontantVerse(rs.getDouble("montant_verse"));
        dette.setMontantRestant(rs.getDouble("montant_restant"));
        dette.setTypeDette(TypeDette.getTypeDetteId(rs.getInt("type_dette_id")));
        dette.setEtatDette(EtatDette.getEtatDetteId(rs.getInt("etat_dette_id")));
        dette.setArchiver(rs.getBoolean("archiver"));

        int clientId = rs.getInt("client_id");
        if (clientId > 0) {
            Client client = clientRepository.selectById(clientId);
            dette.setClient(client);
        }
        dette.setDate(rs.getDate("date").toLocalDate());

        dette.setListeDetails(getDetailsByDetteId(dette.getId()));

        if (getPaiementsByDetteId(dette.getId())!=null) {
            dette.setListePaiement(getPaiementsByDetteId(dette.getId()));
        }

        return dette;
    }

    private List<Details> getDetailsByDetteId(int detteId) {
        List<Details> detailsList = new ArrayList<>();
        String query = "SELECT * FROM details WHERE dette_id = ?";
        try (Connection conn = connexion(); 
        PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, detteId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Details detail = new Details();
                    detail.setQteDette(rs.getDouble("qte_dette"));
                    detail.setArticle(articleRepository.selectById(rs.getInt("article_id")));
                    detailsList.add(detail);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detailsList;
    }


    private List<Paiement> getPaiementsByDetteId(int detteId) {
        List<Paiement> paiementsList = new ArrayList<>();
        String query = "SELECT * FROM paiement WHERE dette_id = ?";
        try (Connection conn = connexion();
        PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, detteId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Paiement paiement = new Paiement();
                    paiement.setMontant(rs.getDouble("montant"));
                    paiement.setDate(rs.getDate("date").toLocalDate());
                    paiementsList.add(paiement);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paiementsList;
    }
    
    @Override
    public Dette selectBy(TypeDette type) {
        Dette dette = null;
        String query = String.format("SELECT * FROM %s WHERE type_dette_id = ?", this.tableName);
        try (Connection conn = connexion(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, TypeDette.getTypeDetteIdAsInt(type));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    dette = converToObject(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'exécution de la requête : " + e.getMessage());
        }
        return dette;
    }

     @Override
    public List<Dette> selectByType(TypeDette type){
        List<Dette> listeDettes = new ArrayList<>();  
        try {
            if (connexion() != null) {
                PreparedStatement ps = connexion().prepareStatement(String.format("SELECT * FROM %s WHERE type_dette_id = ?", this.tableName));
                ps.setInt(1, TypeDette.getTypeDetteIdAsInt(type));  
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Dette dette = converToObject(rs);
                    listeDettes.add(dette);  
                }
            } else {
                System.out.println("Erreur de connexion à la base de données.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'exécution de la requête : " + e.getMessage());
        }

        return listeDettes;  
    }
    @Override
    public void remove(Dette dette) {
        String query = String.format("DELETE FROM %s WHERE id = ?", this.tableName);
        try (Connection conn = connexion(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, dette.getId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Dette supprimée avec succès.");
            } else {
                System.out.println("Aucune dette trouvée avec cet ID.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }

    @Override
    protected String getTableName() {
       return this.tableName;
    }

       
    public boolean update(Dette dette) {
        if (dette.getMontantRestant() == 0) {
            dette.setTypeDette(TypeDette.Solde);
        }  
        
        String query = String.format("UPDATE %s SET montant = ?, montant_verse = ?, montant_restant = ?, type_dette_id = ?, archiver = ?, client_id = ?, date = ?, etat_dette_id = ? WHERE id = ?",
            this.tableName
        );
        
        try (Connection conn = connexion();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setDouble(1, dette.getMontant());
            ps.setDouble(2, dette.getMontantVerse());
            ps.setDouble(3, dette.getMontantRestant());
            ps.setInt(4, TypeDette.getTypeDetteIdAsInt(dette.getTypeDette()));
            ps.setBoolean(5, dette.isArchiver());
            ps.setInt(6, dette.getClient().getId());
            ps.setDate(7, java.sql.Date.valueOf(dette.getDate()));
            ps.setInt(8, dette.getId());  
            ps.setInt(9, EtatDette.getEtatDetteIdAsInt(dette.getEtatDette()));

                               
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
    
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Dette> selectByEtat(EtatDette etat) {
        List<Dette> listeDettes = new ArrayList<>();  
        try {
            if (connexion() != null) {
                PreparedStatement ps = connexion().prepareStatement(String.format("SELECT * FROM %s WHERE etat_dette_id = ?", this.tableName));
                ps.setInt(1, EtatDette.getEtatDetteIdAsInt(etat));  
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Dette dette = converToObject(rs);
                    listeDettes.add(dette);  
                }
            } else {
                System.out.println("Erreur de connexion à la base de données.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'exécution de la requête : " + e.getMessage());
        }

        return listeDettes;  
    }

}

