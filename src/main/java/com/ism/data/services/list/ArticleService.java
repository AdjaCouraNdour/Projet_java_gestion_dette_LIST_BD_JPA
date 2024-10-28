package com.ism.data.services.list;

import java.util.List;

import com.ism.data.entities.Article;
import com.ism.data.enums.EtatArticle;
import com.ism.data.repository.interfaces.ArticleRepositoryI;
import com.ism.data.services.interfaces.ArticleServiceI;

public class ArticleService implements ArticleServiceI {

    ArticleRepositoryI repo;

    public ArticleService(ArticleRepositoryI repo) {
        this.repo = repo;
    }

    @Override
    public Article getById(int id) {
        return repo.selectById(id);
      
    }

    @Override
    public Article getBy(EtatArticle etat) {
        return repo.selectBy(etat);      
    }

    @Override
    public boolean save(Article object) {
        return repo.insert(object);
    }

    @Override
    public List<Article> show() {
        return repo.selectAll();
    }

    public boolean mettreAJour(Article article) {
        Article art = getById(article.getId());
    
        if (art != null) {
            art.setLibelle(article.getLibelle());
            art.setQteStock(article.getQteStock());
            art.setPrix(article.getPrix());
            art.setEtatArticle(article.getEtatArticle());
    
            return repo.update(art);  
        }
        return false; // Retourne false si l'article n'est pas trouvé
    }
    

    @Override
    public void effacer(Article object) {
        repo.remove(object);
    }

    @Override
    public List<Article> getByArticleEtat(EtatArticle etat) {
        return  repo.selectByEtat(etat);
    }
    
    public boolean update(Article article) {
        return repo.update(article);

    }
}
