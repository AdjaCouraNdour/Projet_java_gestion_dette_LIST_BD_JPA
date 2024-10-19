package com.ism.data.services.interfaces;

import java.util.List;

import com.ism.core.Services.Service;
import com.ism.data.entities.Article;
import com.ism.data.enums.EtatArticle;

public interface ArticleServiceI extends Service<Article>{
    Article getBy(EtatArticle etat);
    boolean mettreAJour(Article article);
    List<Article> getByArticleEtat(EtatArticle etat);

    // boolean updateArticle(Article article);

}
