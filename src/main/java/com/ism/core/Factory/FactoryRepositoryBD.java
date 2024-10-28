package com.ism.core.Factory;


import com.ism.data.repository.bd.ArticleRepositoryBD;
import com.ism.data.repository.bd.ClientRepositoryBD;
import com.ism.data.repository.bd.DetailsRepositoryBD;
import com.ism.data.repository.bd.DetteRepositoryBD;
import com.ism.data.repository.bd.PaiementRepositoryBD;
import com.ism.data.repository.bd.UserRepositoryBD;
import com.ism.data.repository.interfaces.ArticleRepositoryI;
import com.ism.data.repository.interfaces.ClientRepositoryI;
import com.ism.data.repository.interfaces.DetailsRepositoryI;
import com.ism.data.repository.interfaces.DetteRepositoryI;
import com.ism.data.repository.interfaces.PaiementRepositoryI;
import com.ism.data.repository.interfaces.UserRepositoryI;

public class FactoryRepositoryBD implements FactoryRepository{
    
    private static ClientRepositoryI clientRepository;
    private static UserRepositoryI userRepository;
    private static ArticleRepositoryI articleRepository=null;
    private static PaiementRepositoryI paiementRepository=null;
    private static DetteRepositoryI detteRepository=null;
    private static DetailsRepositoryI detailsRepository=null;

    @Override
	public ClientRepositoryI getInstanceRepoClient(){
      
        if (clientRepository==null) {  
            return clientRepository = new ClientRepositoryBD();
        }
        // ((ClientRepositoryBD) clientRepository).setUserRepository(getInstanceRepoUser());

        return clientRepository;
    }

    @Override
    public UserRepositoryI getInstanceRepoUser(){
        if (userRepository==null) {
            return userRepository=new UserRepositoryBD(getInstanceRepoClient());
        }
        // ((UserRepositoryBD) userRepository).setClientRepository(getInstanceRepoClient());
        return userRepository;
    }   

    @Override
    public ArticleRepositoryI getInstanceRepoArticle(){
        if (articleRepository==null) {
            return articleRepository=new ArticleRepositoryBD();
        }
        return articleRepository;
    } 

    @Override 
    public DetteRepositoryI getInstanceRepoDette(){

        if (detteRepository==null) {
            return detteRepository=new DetteRepositoryBD(getInstanceRepoClient(),getInstanceRepoArticle());  
        }
        // ((DetteRepositoryBD) detteRepository).setClientRepository(getInstanceRepoClient());
        // ((DetteRepositoryBD) detteRepository).setArticleRepository(getInstanceRepoArticle());
        return detteRepository;
    } 
    
    @Override
    public PaiementRepositoryI getInstanceRepoPaiement(){
        if (paiementRepository==null) {
            return paiementRepository=new PaiementRepositoryBD(getInstanceRepoDette());
        }
        // ((PaiementRepositoryBD) paiementRepository).setDetteRepository(getInstanceRepoDette());
        return paiementRepository;
    } 

    @Override
    public DetailsRepositoryI getInstanceRepoDetails(){
        if (detailsRepository==null) {
            return detailsRepository=new DetailsRepositoryBD(getInstanceRepoDette(),getInstanceRepoArticle());
        }
        return detailsRepository;
    } 
}