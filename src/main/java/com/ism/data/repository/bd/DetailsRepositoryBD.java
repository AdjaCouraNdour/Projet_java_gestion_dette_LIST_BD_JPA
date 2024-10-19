package com.ism.data.repository.bd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ism.core.Repository.RepositoryBDImpl;
import com.ism.data.entities.Article;
import com.ism.data.entities.Details;
import com.ism.data.entities.Dette;
import com.ism.data.repository.interfaces.ArticleRepositoryI;
import com.ism.data.repository.interfaces.DetteRepositoryI;
import com.ism.data.repository.interfaces.DetailsRepositoryI;

public class DetailsRepositoryBD extends RepositoryBDImpl<Details> implements DetailsRepositoryI {

    private DetteRepositoryI detteRepository;
    private ArticleRepositoryI articleRepository;

    public DetailsRepositoryBD(DetteRepositoryI detteRepository, ArticleRepositoryI articleRepository) {
        this.detteRepository = detteRepository;
        this.articleRepository = articleRepository;
        this.tableName = "detail";
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, Details detail) throws SQLException {
        ps.setDouble(1, detail.getQteDette());
        ps.setInt(2, detail.getDette().getId());
        ps.setInt(3, detail.getArticle().getId());
    }

    @Override
    protected String generateInsertQuery() {
        return String.format("INSERT INTO %s (qte_dette, dette_id, article_id) VALUES (?, ?, ?)", this.tableName);
    }

    @Override
    protected String getTableName() {
        return this.tableName;
    }

    @Override
    protected Details converToObject(ResultSet rs) throws SQLException {
        Details details = new Details();
        details.setId(rs.getInt("id"));
        details.setQteDette(rs.getDouble("qte_dette"));
        // Récupération de l'objet Dette à partir de son ID
        int detteId = rs.getInt("dette_id");
        if (detteId > 0) {
            Dette dette = detteRepository.selectById(detteId);
            details.setDette(dette);
        }
        // Récupération de l'objet Article à partir de son ID
        int articleId = rs.getInt("article_id");
        if (articleId > 0) {
            Article article = articleRepository.selectById(articleId);
            details.setArticle(article);
        }

        return details;
    }

    @Override
    protected void setIdFromResultSet(Details object, ResultSet rs) throws SQLException {
        object.setId(rs.getInt(1));
    }

    @Override
    public void remove(Details details) {
        String query = String.format("DELETE FROM %s WHERE id = ?", this.tableName);
        try {
            if (connexion() != null) {
                PreparedStatement ps = connexion().prepareStatement(query);
                ps.setInt(1, details.getId());

                int affectedRows = ps.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Détail supprimé avec succès.");
                } else {
                    System.out.println("Aucun détail trouvé avec cet ID.");
                }
            } else {
                System.out.println("Erreur de connexion à la base de données.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }
}
