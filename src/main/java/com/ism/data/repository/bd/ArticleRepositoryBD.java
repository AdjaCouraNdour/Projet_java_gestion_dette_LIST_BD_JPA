package com.ism.data.repository.bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ism.core.Repository.RepositoryBDImpl;
import com.ism.data.entities.Article;
import com.ism.data.enums.EtatArticle;
import com.ism.data.repository.interfaces.ArticleRepositoryI;

public class ArticleRepositoryBD extends RepositoryBDImpl<Article> implements ArticleRepositoryI {

    public ArticleRepositoryBD() {
        this.tableName = "article";
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, Article object) throws SQLException {
        ps.setString(1, object.getReference());
        ps.setString(2, object.getLibelle());
        ps.setInt(3, object.getPrix());
        ps.setDouble(4, object.getQteStock());
        ps.setInt(5, object.getEtatArticle().ordinal() + 1);

    }

    @Override
    protected String generateInsertQuery() {
        return String.format("INSERT INTO %s (reference, libelle, prix, qte_stock, etat_article_id) VALUES (?, ?, ? ,? ,?)", this.tableName);
    }

    @Override
    protected String getTableName() {
        return this.tableName;
    }

    @Override
    protected Article converToObject(ResultSet rs) throws SQLException {
        Article art = new Article();
        art.setId(rs.getInt("id"));
        art.setReference(rs.getString("reference"));
        art.setLibelle(rs.getString("libelle"));
        art.setPrix(rs.getInt("prix"));
        art.setQteStock(rs.getDouble("qte_stock"));
        art.setEtatArticle(EtatArticle.getEtatArticleId(rs.getInt("etat_article_id")));

        return art;
    }

    @Override
    protected void setIdFromResultSet(Article object, ResultSet rs) throws SQLException {
        object.setId(rs.getInt("id"));
    }

    @Override
    public List<Article> selectByEtat(EtatArticle etat){
        List<Article> articles = new ArrayList<>();  
        try {
            if (connexion() != null) {
                PreparedStatement ps = connexion().prepareStatement(String.format("SELECT * FROM %s WHERE etat_article_id = ?", this.tableName));
                ps.setInt(1, EtatArticle.getEtatArticleIdAsInt(etat));  
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Article article = converToObject(rs);
                    articles.add(article);  
                }
            } else {
                System.out.println("Erreur de connexion à la base de données.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'exécution de la requête : " + e.getMessage());
        }

        return articles;  
    }

    @Override
    public Article selectBy(EtatArticle etat) {
        Article article = null;
        try {
            if (connexion() != null) {
                PreparedStatement ps = connexion().prepareStatement(
                    String.format("SELECT * FROM %s WHERE etat_article_id = ? ", this.tableName));
                ps.setInt(1, EtatArticle.getEtatArticleIdAsInt(etat)); 
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    article = converToObject(rs);
                }
                rs.close();
            } else {
                System.out.println("Erreur de connexion à la BD");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la requête : " + e.getMessage());
        }
        return article;
    }
    
   @Override
    public boolean update(Article article) {
        String query = String.format("UPDATE %s SET reference = ?, libelle = ?, prix = ?, qte_stock = ?, etat_article_id = ? WHERE id = ?", this.tableName);
        
        try (Connection conn = connexion(); // Connexion gérée en try-with-resources
            PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, article.getReference());
            ps.setString(2, article.getLibelle());
            ps.setDouble(3, article.getPrix());
            ps.setDouble(4, article.getQteStock());
            ps.setInt(5, EtatArticle.getEtatArticleIdAsInt(article.getEtatArticle())); // Conversion enum
            ps.setInt(6, article.getId());

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'exécution de la requête : " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public void remove(Article article) {
        String query = String.format("DELETE FROM %s WHERE id = ?", this.tableName);
        try {
            if (connexion() != null) {
                PreparedStatement ps = connexion().prepareStatement(query);
                ps.setInt(1, article.getId());
    
                int affectedRows = ps.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Article supprimé avec succès");
                } else {
                    System.out.println("Aucun article trouvé avec cet ID");
                }
            } else {
                System.out.println("Erreur de connexion à la BD");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }
    
}
