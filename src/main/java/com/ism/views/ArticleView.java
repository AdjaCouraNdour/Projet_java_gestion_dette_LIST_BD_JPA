package com.ism.views;

import java.util.List;
import java.util.Scanner;

import com.ism.core.view.ViewImpl;
import com.ism.data.entities.Article;
import com.ism.data.enums.EtatArticle;
import com.ism.data.services.list.ArticleService;

public class ArticleView extends ViewImpl<Article> {

    private ArticleService artService;

    public ArticleView(Scanner scanner, ArticleService artService) {
        super(scanner);
        this.artService = artService;
    }

    @Override
    public Article create() {
        Article art =new Article();
        scanner.nextLine();

        System.out.println("veiller entre le libelle de l'article");
        art.setLibelle(saisieChamp());
        System.out.println("veiller entre le prix de l'article");
        art.setPrix(saisiePrix());
        System.out.println("entrer une quantite pour larticle");
        art.setQteStock(saisieQTE());
        
        if (art.getQteStock()==0) {
            art.setEtatArticle(EtatArticle.Indisponible);
        } else {
            art.setEtatArticle(EtatArticle.Disponible);
        }
       return art;
    }


    public List<Article> listerArticleDispo() {
        List<Article> artDispo = artService.getByArticleEtat(EtatArticle.Disponible);
        if (artDispo.isEmpty()) {
            System.out.println("Il n'y a pas d'article disponible pour le moment");
            return null;
        } else {
            System.out.println("Articles disponibles :");
            for (Article article : artDispo) {
                System.out.println(article); 
            }
            return artDispo; 
        }
    }
    
    public Article mettreAJour() {
        // Vérifie s'il y a des articles à afficher
        if (artService.show().isEmpty()) {
            System.out.println("Il n'y a pas encore d'articles, veuillez en créer d'abord.");
            return null;
        } else {

            System.out.println(artService.show());
            System.out.println("Choisissez l'article à mettre à jour :");
            Article art=artService.getById(scanner.nextInt());
            if (art == null) {
                System.out.println("Article non trouvé.");
                return null;
            }
    
            System.out.println("--- MISE À JOUR ---");
            System.out.println("1- Le libellé");
            System.out.println("2- La quantité");
            System.out.println("3- Le prix");
            System.out.println("4- Abandonner");
    
            int choix = scanner.nextInt();
            switch (choix) {
                case 1 -> {
                    scanner.nextLine(); 
                    System.out.println("Entrez un nouveau libellé pour l'article :");
                    art.setLibelle(scanner.nextLine());
                }
                case 2 -> {
                    int qte;
                    do {
                        System.out.println("Entrez une nouvelle quantité pour l'article :");
                        qte = scanner.nextInt();
                    } while (qte < 0);
                    art.setQteStock(qte);
                }
                case 3 -> {
                    int prix;
                    do {
                        System.out.println("Entrez le nouveau prix pour l'article :");
                        prix = scanner.nextInt();
                    } while (prix < 0);
                    art.setPrix(prix);
                }
                case 4 -> {
                    System.out.println("Mise à jour annulée.");
                    return null;
                }
                default -> {
                    System.out.println("Choix invalide. Mise à jour annulée.");
                    return null;
                }
            }
    
            // Mettre à jour au niveau du service
            if (artService.mettreAJour(art)) {
                System.out.println("Article mis à jour avec succès.");
                return art;
            } else {
                System.out.println("Échec de la mise à jour.");
                return null;
            }
        }
    }

    public String saisieChamp() {
        String champ = "";
        boolean isValid = false;
        while (!isValid) {
            champ = scanner.nextLine().trim();
            if (champ.isEmpty()) {
                System.out.println("Erreur : ce champ ne doit pas être vide.");
            } else {
                isValid = true;
            }
        }
        return champ;
    }
   
    public int saisiePrix() {
        String input;
        int prix = 0;
        boolean isValid = false;
        while (!isValid) {
            input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Erreur : ce champ ne doit pas être vide.");
            } else if (!input.matches("\\d+")) { 
                System.out.println("Erreur : veuillez entrer un nombre valide (des chiffres uniquement).");
            } else {
                prix = Integer.parseInt(input); 
                if (prix <= 0) {
                    System.out.println("Erreur : le prix doit être supérieur à zéro.");
                } else {
                    isValid = true;
                }
            }
        }
        return prix;
    }

    public int saisieQTE() {
        String input;
        int qte = 0;
        boolean isValid = false;
        while (!isValid) {
            input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Erreur : ce champ ne doit pas être vide.");
            } else if (!input.matches("\\d+")) { 
                System.out.println("Erreur : veuillez entrer un nombre valide (des chiffres uniquement).");
            } else {
                qte = Integer.parseInt(input);
                isValid = true;
            }
        }
        return qte;
    }
    
}
