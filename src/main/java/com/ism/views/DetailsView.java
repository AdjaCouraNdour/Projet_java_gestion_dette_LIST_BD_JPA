package com.ism.views;

import java.util.Scanner;

import com.ism.core.view.ViewImpl;
import com.ism.data.entities.Article;
import com.ism.data.entities.Details;
import com.ism.data.enums.EtatArticle;
import com.ism.data.services.list.ArticleService;

public class DetailsView extends ViewImpl<Details> {
    
    private ArticleService artService;
    
    public DetailsView(Scanner scanner, ArticleService artService) {
        super(scanner);
        this.artService = artService;
    }

    @Override
    public Details create() {
        Details details =new Details();
        scanner.nextLine();
            Article art=askArticle();
            if (art==null) {
                return null;
            }else{
                details.setArticle(art);
                int qte;
                do {
                    System.out.println("entrer une qtedette pour larticle");
                    qte=scanner.nextInt();
                } while ( qte<=0 || qte > art.getQteStock());
                details.setQteDette(qte);
            return details;
        }
    }

    private Article askArticle() {

        if (artService.getBy(EtatArticle.Disponible) == null) {
            System.out.println("Il n'y a pas encore d'article disponible.");
            return null;
        } else {
            System.out.println(artService.getBy(EtatArticle.Disponible));
            Article art = null;
            do {
                System.out.println("Veuillez choisir l'id de l'article dans la liste proposée :");
                System.out.println("tapez 0 pour abandonner");

                if (scanner.hasNextInt()) {
                    int article = scanner.nextInt();
                    art = artService.getById(article);
                    
                    if (article == 0) {
                        System.out.println("Opération annulée.");
                        return null;
                    }
                    if (art == null) {
                        System.out.println("Cet article n'existe pas. Veuillez entrer un id existant.");
                    } else if (art.getEtatArticle() != EtatArticle.Disponible) {
                        System.out.println("L'article sélectionné n'est pas disponible. Veuillez choisir un autre article.");
                        art = null; 
                    }
                } else {
                    System.out.println("Id invalide.");
                    scanner.next(); 
                }

            } while (art == null); 
            return art;
        }
    }
   
}
