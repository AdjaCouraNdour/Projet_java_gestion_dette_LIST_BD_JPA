package com.ism.data.repository.jpa;

import java.util.List;
import com.ism.core.Repository.RepositoryJPA;
import com.ism.data.entities.Article;
import com.ism.data.enums.EtatArticle;
import com.ism.data.repository.interfaces.ArticleRepositoryI;
import jakarta.persistence.TypedQuery;

public class ArticleRepositoryJPA extends RepositoryJPA<Article> implements ArticleRepositoryI {

    public ArticleRepositoryJPA( Class<Article> type) {
        super(type);
    }

    // Méthode pour sélectionner un article par son identifiant
    @Override
    public Article selectById(int id) {
        try {
            return em.find(Article.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Méthode pour sélectionner un article en fonction de son état
    @Override
    public Article selectBy(EtatArticle etat) {
        try {
            TypedQuery<Article> query = em.createQuery(
                "SELECT a FROM Article a WHERE a.etatArticle = :etat", Article.class);
            query.setParameter("etat", etat);
            return query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Méthode pour sélectionner une liste d'articles en fonction de leur état
    @Override
    public List<Article> selectByEtat(EtatArticle etat) {
        try {
            TypedQuery<Article> query = em.createQuery(
                "SELECT a FROM Article a WHERE a.etatArticle = :etat", Article.class);
            query.setParameter("etat", etat);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean update(Article article) {
        try {
            em.getTransaction().begin();
            em.merge(article); 
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback(); 
            }
            return false;
        }
    }

}
