package com.ism.core.Factory;

import com.ism.data.entities.Article;
import com.ism.data.entities.Client;
import com.ism.data.entities.Details;
import com.ism.data.entities.Dette;
import com.ism.data.entities.Paiement;
import com.ism.data.entities.User;
import com.ism.data.repository.interfaces.ArticleRepositoryI;
import com.ism.data.repository.interfaces.ClientRepositoryI;
import com.ism.data.repository.interfaces.DetailsRepositoryI;
import com.ism.data.repository.interfaces.DetteRepositoryI;
import com.ism.data.repository.interfaces.PaiementRepositoryI;
import com.ism.data.repository.interfaces.UserRepositoryI;
import com.ism.data.repository.jpa.ArticleRepositoryJPA;
import com.ism.data.repository.jpa.ClientRepositoryJPA;
import com.ism.data.repository.jpa.DetailsRepositoryJPA;
import com.ism.data.repository.jpa.DetteRepositoryJPA;
import com.ism.data.repository.jpa.PaiementRepositoryJPA;
import com.ism.data.repository.jpa.UserRepositoryJPA;

import jakarta.persistence.*;

public class FactoryRepositoryJPA implements FactoryRepository{
    
    private static ClientRepositoryI clientRepository;
    private static UserRepositoryI userRepository;
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("POSTGRESQLDETTES");
    private static EntityManager em;
    private static ArticleRepositoryI articleRepository;
    private static PaiementRepositoryI paiementRepository;
    private static DetteRepositoryI detteRepository;
    private static DetailsRepositoryI detailsRepository;
    
    @Override
	public ClientRepositoryI getInstanceRepoClient(){
        if (clientRepository==null) {
             em = emf.createEntityManager();
            clientRepository = new ClientRepositoryJPA(em, Client.class,userRepository);
        }
        return clientRepository;

    }

    @Override
    public UserRepositoryI getInstanceRepoUser(){
        if (userRepository==null) {
            em=emf.createEntityManager();
            userRepository=new UserRepositoryJPA(em,User.class,clientRepository);
        }
        return userRepository;
    }   

    @Override
    public ArticleRepositoryI getInstanceRepoArticle(){
        if (articleRepository==null) {
            em=emf.createEntityManager();
            articleRepository=new ArticleRepositoryJPA(em,Article.class);
        }
        return articleRepository;
    }  

    @Override
    public DetteRepositoryI getInstanceRepoDette(){
        if (detteRepository==null) {
            em=emf.createEntityManager();
            detteRepository=new DetteRepositoryJPA(em,Dette.class);    
            }
        return detteRepository;
    }   

    @Override
    public PaiementRepositoryI getInstanceRepoPaiement(){
        if (paiementRepository==null) {
            em=emf.createEntityManager();
            paiementRepository=new PaiementRepositoryJPA(em,Paiement.class);        }
        return paiementRepository;
    } 
      
    @Override
    public DetailsRepositoryI getInstanceRepoDetails(){
        if (detailsRepository==null) {
            em=emf.createEntityManager();
            detailsRepository =new DetailsRepositoryJPA(em,Details.class);        }
        return detailsRepository;
    }  

}
