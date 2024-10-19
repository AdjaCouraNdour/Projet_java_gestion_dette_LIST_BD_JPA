package com.ism;

import java.util.Scanner;
import com.ism.core.Factory.FactoryService;
import com.ism.views.ArticleView;
import com.ism.views.ClientView;
import com.ism.views.DetailsView;
import com.ism.views.DetteView;
import com.ism.views.PaiementView;
import com.ism.views.UserView;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        FactoryService factoryService = new FactoryService();

        DetailsView detailsView = new DetailsView(scanner, factoryService.getInstanceArticleService());
        ClientView clientView = new ClientView(scanner, factoryService.getInstanceClientService());
        DetteView detteView = new DetteView(scanner, detailsView, factoryService.getInstanceDetteService(), clientView);
        UserView userView = new UserView(scanner, factoryService.getInstanceClientService(),factoryService.getInstanceUserService(), clientView);
        ArticleView articleView = new ArticleView(scanner, factoryService.getInstanceArticleService());
        PaiementView paiementView = new PaiementView(scanner, detteView, clientView);

        int choix;
        do {
            choix = menu();
            switch (choix) {
                case 1 -> {
                    factoryService.getInstanceClientService().save(clientView.create());
                }
                case 2 -> {
                    if (factoryService.getInstanceClientService().show().isEmpty()) {
                        System.out.println("il n'y a pas de client pour le moment");
                    } else {
                        clientView.afficher(factoryService.getInstanceClientService().show());
                    }
                }
                case 3 -> {
                    factoryService.getInstanceUserService().save(userView.create());
                }
                case 4 -> {
                    if (factoryService.getInstanceUserService().show().isEmpty()) {
                        System.out.println("il n'y a pas de user pour le moment");
                    } else {
                        userView.afficher(factoryService.getInstanceUserService().show());
                    }
                }
                case 5 -> {
                    userView.listerLesUtulisateur();
                }
                case 6 -> {
                    factoryService.getInstanceArticleService().save(articleView.create());
                }
                case 7 -> {
                    if (factoryService.getInstanceArticleService().show().isEmpty()) {
                        System.out.println("il n'y a pas d'article pour le moment");
                    } else {
                        articleView.afficher(factoryService.getInstanceArticleService().show());
                    }
                }
                case 8 -> {
                    articleView.listerArticleDispo();
                }
                case 9 -> {
                    articleView.mettreAJour();
                }
                case 10 -> {
                    factoryService.getInstanceDetteService().save(detteView.create());
                }
                case 11 -> {
                    detteView.listerDetteClient();
                }
                case 12 -> {
                    factoryService.getInstancePaiementService().save(paiementView.create());
                }
                case 13 -> {
                    detteView.archiverDetteSolde();
                }
                case 14 -> {
                    System.out.println("bizzzzzzoooou !!! <3");
                }
            }
        } while (choix != 15);
    }

    public static int menu() {
        System.out.println("1-Créer un client");
        System.out.println("2-Lister les clients");
        System.out.println("3-Creer compte utulisateur pour un client");
        System.out.println("4-Lister les comptes utulisateur ");
        System.out.println("5-Lister les comptes utulisateur par role ou par etatActif");
        System.out.println("6-creer un article");
        System.out.println("7-lister les articles");
        System.out.println("8-lister les articles disponibles");
        System.out.println("9-mettre a jour la quantite stock d'un article");
        System.out.println("10-faire une dette");
        System.out.println("11-Lister les dettes d'un client");
        System.out.println("12-faire un paiement");
        System.out.println("13-archiver les dettes soldes");
        System.out.println("14-Quitter");
        return scanner.nextInt();
    }
}