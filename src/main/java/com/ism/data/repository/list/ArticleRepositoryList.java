package com.ism.data.repository.list;

import java.util.List;
import java.util.Optional;

import com.ism.core.Repository.RepositoryImpl;
import com.ism.data.entities.Article;
import com.ism.data.enums.EtatArticle;
import com.ism.data.repository.interfaces.ArticleRepositoryI;

public class ArticleRepositoryList extends RepositoryImpl<Article> implements ArticleRepositoryI {

    private int lastId = 0;

    @Override
    public boolean insert(Article object) {
        if (object == null) {
            return false; 
        }
        object.setId(++lastId);
        super.insert(object);
        return true;
    }

    @Override
    public Article selectBy(EtatArticle etat) {
        if (etat == null) {
            return null; 
        }
        return list.stream()
                   .filter(article -> article.getEtatArticle() != null && article.getEtatArticle() == etat)
                   .findFirst()
                   .orElse(null);
    }

    @Override
    public boolean update(Article article) {
        if (article == null || article.getId() == 0) {
            return false; 
        }
        Optional<Article> existingArticleOpt = list.stream()
                                                   .filter(a -> a.getId() == article.getId())
                                                   .findFirst();
        if (existingArticleOpt.isPresent()) {
            Article existingArticle = existingArticleOpt.get();

            if (article.getReference() != null) {
                existingArticle.setReference(article.getReference());
            }
            if (article.getLibelle() != null) {
                existingArticle.setLibelle(article.getLibelle());
            }
            existingArticle.setPrix(article.getPrix()); // Prix est un double, pas besoin de null check
            existingArticle.setQteStock(article.getQteStock()); // Stock est un int, pas besoin de null check
            if (article.getEtatArticle() != null) {
                existingArticle.setEtatArticle(article.getEtatArticle());
            }
            return true; 
        }
        return false; 
    }

    @Override
    public List<Article> selectByEtat(EtatArticle etat) {
        if (etat == null) {
            return List.of(); 
        }
        return list.stream()
                   .filter(art -> art.getEtatArticle() != null && art.getEtatArticle() == etat)
                   .toList();
    }
}
