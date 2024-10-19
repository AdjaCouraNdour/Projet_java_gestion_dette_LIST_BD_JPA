package com.ism.core.Factory;

import java.util.*;

import com.ism.core.Services.YamlService;
import com.ism.core.Services.YamlServiceImpl;
import com.ism.data.services.list.ArticleService;
import com.ism.data.services.list.ClientService;
import com.ism.data.services.list.DetailsService;
import com.ism.data.services.list.DetteService;
import com.ism.data.services.list.PaiementService;
import com.ism.data.services.list.UserService;
import java.lang.String;

public class FactoryService  {

    private static ClientService clientService ;
    private static UserService userService;
    private static ArticleService articleService;
    private static DetteService detteService;
    private static PaiementService paiementService;
    private static DetailsService detailsService ;
    private FactoryRepository factory ;
    YamlService yamlService ;
    
    public FactoryService() {
        yamlService = new YamlServiceImpl();
        Map<String, Object> mapYaml = yamlService.loadYaml();
        String repoType = mapYaml.get("repo").toString();

        switch (repoType) {

            case "RepositoryJPA":
                FactoryRepositoryJPA factoryRepositoryJPA = new FactoryRepositoryJPA();
                factory = factoryRepositoryJPA;
                break;
            case "RepositoryBD":
                FactoryRepositoryBD factoryRepositoryBD = new FactoryRepositoryBD();
                factory = factoryRepositoryBD;
                break;
            case "RepositoryList":
                FactoryRepositoryList factoryRepositoryList = new FactoryRepositoryList();
                factory = factoryRepositoryList;
                break;
        }
    
    }

    public ClientService getInstanceClientService(){
        if (clientService==null) {
            return clientService=new ClientService(factory.getInstanceRepoClient());
        }
        return clientService;
    }

    public UserService getInstanceUserService(){
        if (userService==null) {
            return userService=new UserService(factory.getInstanceRepoUser());
        }
        return userService;
    }

    public ArticleService getInstanceArticleService(){
        if (articleService==null) {
            return articleService=new ArticleService(factory.getInstanceRepoArticle());
        }
        return articleService;
    }
    public DetteService getInstanceDetteService(){
        if (detteService==null) {
            return detteService=new DetteService(factory.getInstanceRepoDette());
        }
        return detteService;
    }
    public PaiementService getInstancePaiementService(){
        if (paiementService==null) {
            return paiementService=new PaiementService(factory.getInstanceRepoPaiement());
        }
        return paiementService;
    }
    public DetailsService getInstanceDetailsService(){
        if (detailsService==null) {
            return detailsService=new DetailsService(factory.getInstanceRepoDetails());
        }
        return detailsService;
    }
   
   
}
