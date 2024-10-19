package com.ism.data.repository.bd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ism.core.Repository.RepositoryBDImpl;
import com.ism.data.entities.Dette;
import com.ism.data.entities.Paiement;
import com.ism.data.repository.interfaces.DetteRepositoryI;
import com.ism.data.repository.interfaces.PaiementRepositoryI;

public class PaiementRepositoryBD extends RepositoryBDImpl<Paiement> implements PaiementRepositoryI {

    private DetteRepositoryI detteRepository;

    public PaiementRepositoryBD(DetteRepositoryI detteRepository) {
        this.detteRepository = detteRepository;
        this.tableName = "paiement";
    }
     public void setDetteRepository(DetteRepositoryI detteRepository) {
        this.detteRepository = detteRepository;
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, Paiement paiement) throws SQLException {
        ps.setDate(1, java.sql.Date.valueOf(paiement.getDate()));
        ps.setDouble(2, paiement.getMontant());
        ps.setInt(3, paiement.getDette().getId());
    }

    @Override
    protected String generateInsertQuery() {
        return String.format("INSERT INTO %s (date, montant, dette_id) VALUES (?, ?, ?)", this.tableName);
    }

    @Override
    protected String getTableName() {
        return this.tableName;
    }

    @Override
    protected Paiement converToObject(ResultSet rs) throws SQLException {
        Paiement paiement = new Paiement();
        paiement.setId(rs.getInt("id"));
        paiement.setDate(rs.getDate("date").toLocalDate());
        paiement.setMontant(rs.getDouble("montant"));

        // Récupérer l'objet Dette à partir de son ID
        int detteId = rs.getInt("dette_id");
        if (detteId > 0) {
            Dette dette = detteRepository.selectById(detteId);
            paiement.setDette(dette);
        }

        return paiement;
    }

    @Override
    protected void setIdFromResultSet(Paiement object, ResultSet rs) throws SQLException {
        object.setId(rs.getInt(1)); // Récupère l'ID généré par la base de données lors de l'insertion
    }

    @Override
    public void remove(Paiement paiement) {
        String query = String.format("DELETE FROM %s WHERE id = ?", this.tableName);
        try {
            if (connexion() != null) {
                PreparedStatement ps = connexion().prepareStatement(query);
                ps.setInt(1, paiement.getId());

                int affectedRows = ps.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Paiement supprimé avec succès.");
                } else {
                    System.out.println("Aucun paiement trouvé avec cet ID.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du paiement : " + e.getMessage());
        }
    }
}
