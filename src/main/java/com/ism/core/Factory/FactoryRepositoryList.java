package com.ism.core.Factory;

import com.ism.data.repository.interfaces.ArticleRepositoryI;
import com.ism.data.repository.interfaces.ClientRepositoryI;
import com.ism.data.repository.interfaces.DetailsRepositoryI;
import com.ism.data.repository.interfaces.DetteRepositoryI;
import com.ism.data.repository.interfaces.PaiementRepositoryI;
import com.ism.data.repository.interfaces.UserRepositoryI;
import com.ism.data.repository.list.ArticleRepositoryList;
import com.ism.data.repository.list.ClientRepositoryList;
import com.ism.data.repository.list.DetteRepositoryList;
import com.ism.data.repository.list.PaiementRepositoryList;
import com.ism.data.repository.list.UserRepositoryList;

public class FactoryRepositoryList implements FactoryRepository {
         
        private static ClientRepositoryI clientRepository;
        private static UserRepositoryI userRepository;
        private static ArticleRepositoryI articleRepository=null;
        private static PaiementRepositoryI paiementRepository=null;
        private static DetteRepositoryI detteRepository=null;
        
        @Override
        public ClientRepositoryI getInstanceRepoClient(){
            if (clientRepository==null) {
                return clientRepository = new ClientRepositoryList();
            }
            return clientRepository;
    
        }

        @Override
        public UserRepositoryI getInstanceRepoUser(){
            if (userRepository==null) {
                return userRepository=new UserRepositoryList();
            }
            return userRepository;
        } 

        @Override
        public ArticleRepositoryI getInstanceRepoArticle(){
            if (articleRepository==null) {
                return articleRepository=new ArticleRepositoryList();
            }
            return articleRepository;
        } 

        @Override 
        public DetteRepositoryI getInstanceRepoDette(){
            if (detteRepository==null) {
                return detteRepository=new DetteRepositoryList();
            }
            return detteRepository;
        } 

        @Override  
        public PaiementRepositoryI getInstanceRepoPaiement(){
            if (paiementRepository==null) {
                return paiementRepository=new PaiementRepositoryList();
            }
            return paiementRepository;
        }
        @Override
        public DetailsRepositoryI getInstanceRepoDetails() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getInstanceRepoDetails'");
        } 
}

