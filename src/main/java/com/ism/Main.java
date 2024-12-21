package com.ism;

import java.util.Scanner;
import com.ism.core.Factory.FactoryService;
import com.ism.data.entities.User;
import com.ism.data.enums.UserRole;
import com.ism.data.services.list.UserConnect;
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
        UserView userView = new UserView(scanner, factoryService.getInstanceClientService(), factoryService.getInstanceUserService(), clientView);
        DetteView detteView = new DetteView(scanner, detailsView, factoryService.getInstanceDetteService(), clientView, factoryService.getInstanceArticleService());
        ArticleView articleView = new ArticleView(scanner, factoryService.getInstanceArticleService());
        PaiementView paiementView = new PaiementView(scanner, detteView, clientView, factoryService.getInstanceDetteService());

        // -----------------------creation des utilisateurs---------------------------
        //---------------- A DECOMMENTER QUE SI ON UTULISE LA VERSION LIST-------------------
            // User userA = new User("admin@gmail.com","AD", "AD",UserRole.Admin,true);
            // factoryService.getInstanceUserService().save(userA);

            // User userB = new User("boutiquier@gmail.com","BOU", "BOU",UserRole.Boutiquier,true);
            // factoryService.getInstanceUserService().save(userB);

        while (true) {  

                System.out.println("Entrez votre login");
                String login = scanner.nextLine();
                System.out.println("Entrez votre mot de passe");
                String password = scanner.nextLine();
                User user = factoryService.getInstanceUserService().getByLogin(login);
                if (user == null || !user.getPassword().equals(password)) {
                    System.out.println("Login ou mot de passe incorrect");
                } else {
                    UserConnect.setUserConnect(user);
                    switch (user.getUserRole()) {
                        case Boutiquier -> {
                            int choice;
                            do {
                                choice = menuBoutiquier();
                                switch (choice) {
                                    case 1 -> factoryService.getInstanceClientService().save(clientView.create());
                                    case 2 -> {
                                        if (factoryService.getInstanceClientService().show().isEmpty()) {
                                            System.out.println("Il n'y a pas de client pour le moment");
                                        } else {
                                            clientView.afficher(factoryService.getInstanceClientService().show());
                                        }
                                    }
                                    case 3 -> factoryService.getInstanceUserService().save(userView.create());
                                    case 4 -> {
                                        if (factoryService.getInstanceUserService().show().isEmpty()) {
                                            System.out.println("Il n'y a pas de user pour le moment");
                                        } else {
                                            userView.afficher(factoryService.getInstanceUserService().show());
                                        }
                                    }
                                    case 5 -> userView.listerLesUtulisateur();
                                    case 6 -> factoryService.getInstanceArticleService().save(articleView.create());
                                    case 7 -> {
                                        if (factoryService.getInstanceArticleService().show().isEmpty()) {
                                            System.out.println("Il n'y a pas d'article pour le moment");
                                        } else {
                                            articleView.afficher(factoryService.getInstanceArticleService().show());
                                        }
                                    }
                                    case 8 -> articleView.listerArticleDispo();
                                    case 9 -> articleView.mettreAJour();
                                    case 10 -> factoryService.getInstanceDetteService().save(detteView.create());
                                    case 11 -> detteView.listerDetteClient();
                                    case 12 -> factoryService.getInstancePaiementService().save(paiementView.create());
                                    case 13 -> detteView.archiverDetteSolde();
                                    case 14 -> detteView.accepterOuRefuserDette();
                                    case 15-> System.out.println("Quitter");
                                }
                            } while (choice != 15); 
                        }
                        case Admin -> {
                            int choix;
                            do {
                                choix = menuAdmin();
                                switch (choix) {
                                    case 1 -> factoryService.getInstanceClientService().save(clientView.create());
                                    case 2 -> {
                                        if (factoryService.getInstanceClientService().show().isEmpty()) {
                                            System.out.println("Il n'y a pas de client pour le moment");
                                        } else {
                                            clientView.afficher(factoryService.getInstanceClientService().show());
                                        }
                                    }
                                    case 3 -> factoryService.getInstanceUserService().save(userView.create());
                                    case 4 -> {
                                        if (factoryService.getInstanceUserService().show().isEmpty()) {
                                            System.out.println("Il n'y a pas de user pour le moment");
                                        } else {
                                            userView.afficher(factoryService.getInstanceUserService().show());
                                        }
                                    }
                                    case 5 -> userView.listerLesUtulisateur();
                                    case 6 -> factoryService.getInstanceArticleService().save(articleView.create());
                                    case 7 -> {
                                        if (factoryService.getInstanceArticleService().show().isEmpty()) {
                                            System.out.println("Il n'y a pas d'article pour le moment");
                                        } else {
                                            articleView.afficher(factoryService.getInstanceArticleService().show());
                                        }
                                    }
                                    case 8 -> articleView.listerArticleDispo();
                                    case 9 -> articleView.mettreAJour();
                                    case 10 -> factoryService.getInstanceDetteService().save(detteView.create());
                                    case 11 -> detteView.listerDetteClient();
                                    case 12 -> System.out.println("Quitter"); 
  
                                }
                            } while (choix != 12); 
                        }
                        case Client -> {
                            int c;
                            do {
                                c = menuClient();
                                switch (c) {
                                    case 1 -> factoryService.getInstanceDetteService().save(detteView.createDette());     
                                    case 2 -> detteView.afficherMesDettes();
                                    case 3 -> detteView.afficherMesDettesRefuser();                                          
                                    case 4 -> System.out.println("Au revoir !");
                                    
                                }

                            } while (c != 4); 
                        }
                    }
                }
            }  
    }

    public static int menuAdmin() {
        System.out.println("1 - Créer un client");
        System.out.println("2 - Lister les clients");
        System.out.println("3 - Créer compte utilisateur pour un client");
        System.out.println("4 - Lister les comptes utilisateur");
        System.out.println("5 - Lister les comptes utilisateur par rôle ou par état actif");
        System.out.println("6 - Créer un article");
        System.out.println("7 - Lister les articles");
        System.out.println("8 - Lister les articles disponibles");
        System.out.println("9 - Mettre à jour la quantité en stock d'un article");
        System.out.println("10 - Faire une dette");
        System.out.println("11 - Lister les dettes d'un client");
        System.out.println("12 -quitter");

        return scanner.nextInt();
    }

    public static int menuClient() {

        System.out.println("1 - Faire une dette");
        System.out.println("2 - Lister mes dettes non-soldées ");
        System.out.println("3 - Lister mes dette refuser");
        System.out.println("4 - Quitter");
        return scanner.nextInt();
    }

    public static int menuBoutiquier() {
        System.out.println("1 - Créer un client");
        System.out.println("2 - Lister les clients");
        System.out.println("3 - Créer compte utilisateur pour un client");
        System.out.println("4 - Lister les comptes utilisateur");
        System.out.println("5 - Lister les comptes utilisateur par rôle ou par état actif");
        System.out.println("6 - Créer un article");
        System.out.println("7 - Lister les articles");
        System.out.println("8 - Lister les articles disponibles");
        System.out.println("9 - Mettre à jour la quantité en stock d'un article");
        System.out.println("10 - Faire une dette");
        System.out.println("11 - Lister les dettes d'un client");
        System.out.println("12 - Faire un paiement");
        System.out.println("13 - Archiver les dettes soldées d'un client");
        System.out.println("14 - Traiter une dette");
        System.out.println("15 - Quitter");
        return scanner.nextInt();
    }
}
