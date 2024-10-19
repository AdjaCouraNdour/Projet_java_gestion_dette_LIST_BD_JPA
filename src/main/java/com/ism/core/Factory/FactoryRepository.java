package com.ism.core.Factory;

import com.ism.data.repository.interfaces.ArticleRepositoryI;
import com.ism.data.repository.interfaces.ClientRepositoryI;
import com.ism.data.repository.interfaces.DetailsRepositoryI;
import com.ism.data.repository.interfaces.DetteRepositoryI;
import com.ism.data.repository.interfaces.PaiementRepositoryI;
import com.ism.data.repository.interfaces.UserRepositoryI;

public interface FactoryRepository {
    
        ClientRepositoryI getInstanceRepoClient();
        UserRepositoryI getInstanceRepoUser();
        ArticleRepositoryI getInstanceRepoArticle();
        DetteRepositoryI getInstanceRepoDette();
        PaiementRepositoryI getInstanceRepoPaiement();
        DetailsRepositoryI getInstanceRepoDetails();
}
