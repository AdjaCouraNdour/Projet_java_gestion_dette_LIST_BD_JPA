package com.ism.data.repository.jpa;


import java.util.List;

import com.ism.core.Repository.RepositoryJPA;
import com.ism.data.entities.Article;
import com.ism.data.enums.EtatArticle;
import com.ism.data.repository.interfaces.ArticleRepositoryI;

import jakarta.persistence.EntityManager;

public class ArticleRepositoryJPA extends RepositoryJPA<Article> implements ArticleRepositoryI {

    public ArticleRepositoryJPA(EntityManager em, Class<Article> type ) {
        super(em, type);
        this.tableName = "\"article\"";
    }

    @Override
    public Article selectById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectById'");
    }

    @Override
    public Article selectBy(EtatArticle etat) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectBy'");
    }

    @Override
    public boolean update(Article article) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public List<Article> selectByEtat(EtatArticle etat) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectByEtat'");
    }

    // @Override
    // public List<Article> selectByEtat(EtatArticle etat) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'selectByEtat'");
    // }
    
}
