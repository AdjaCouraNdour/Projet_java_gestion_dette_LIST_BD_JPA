package com.ism.data.repository.list;


import java.util.List;
import java.util.Optional;

import com.ism.core.Repository.RepositoryImpl;
import com.ism.data.entities.Article;
import com.ism.data.enums.EtatArticle;
import com.ism.data.repository.interfaces.ArticleRepositoryI;

public class ArticleRepositoryList extends RepositoryImpl<Article> implements ArticleRepositoryI {

    @Override
    public Article selectBy(EtatArticle etat) {
        return
        list.stream()
        .filter(article -> article.getEtatArticle()==etat)
        .findFirst()
        .orElse(null);
    }

   @Override
    public boolean update(Article article) {
        // Recherche l'article par ID dans la liste
        Optional<Article> existingArticleOpt = list.stream()
                                                   .filter(a -> a.getId() == article.getId())
                                                   .findFirst();
        
        if (existingArticleOpt.isPresent()) {
            Article existingArticle = existingArticleOpt.get();

            // Mise à jour des propriétés de l'article existant
            existingArticle.setReference(article.getReference());
            existingArticle.setLibelle(article.getLibelle());
            existingArticle.setPrix(article.getPrix());
            existingArticle.setQteStock(article.getQteStock());
            existingArticle.setEtatArticle(article.getEtatArticle());

            return true; // Mise à jour réussie
        }

        return false; // Article non trouvé
    }

    @Override
    public List<Article> selectByEtat(EtatArticle etat) {
        return list.stream()
                .filter(art -> art.getEtatArticle() == etat) 
                .toList();
    }
    
}
