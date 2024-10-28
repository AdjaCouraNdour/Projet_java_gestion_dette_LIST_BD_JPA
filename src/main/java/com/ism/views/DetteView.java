package com.ism.views;

import java.util.List;
import java.util.Scanner;

import com.ism.core.view.ViewImpl;
import com.ism.data.entities.Article;
import com.ism.data.entities.Client;
import com.ism.data.entities.Details;
import com.ism.data.entities.Dette;
import com.ism.data.enums.TypeDette;
import com.ism.data.services.list.ArticleService;
import com.ism.data.services.list.DetteService;
import com.ism.data.services.list.UserConnect;


public class DetteView extends ViewImpl<Dette>{

    private DetailsView detailsView ;
    private DetteService detteService;
    private ClientView clientView;
    private ArticleService articleService;
    
    public DetteView(Scanner scanner, DetailsView detailsView,
            DetteService detteService,ClientView clientView,ArticleService articleService) {
        super(scanner);
        this.detailsView = detailsView;
        this.detteService = detteService;
        this.clientView = clientView;
        this.articleService = articleService;

    }


    @Override
    public Dette create() {
        Dette dette =new Dette();
        scanner.nextLine();
        Client cl=clientView.askClient();
        if (cl==null) {
            return null;
        }else{
            Details details= detailsView.create();
            if (details!=null) {
                dette.setTypeDette(TypeDette.nonSolde);
                details.setDette(dette);
                cl.setListeDette(dette); 
                dette.setClient(cl);          
                dette.setListeDetails(details); 
                Article art = details.getArticle();
                if (details.getQteDette() > art.getQteStock()) {
                    System.out.println("Erreur: La quantité demandée dépasse le stock disponible.");
                    return null;
                }
                dette.setMontant(details.getQteDette() * art.getPrix());
                art.setQteStock(art.getQteStock() - details.getQteDette());
                dette.setMontantVerse(0);
                dette.setMontantRestant(dette.getMontant() - dette.getMontantVerse());
                // detteService.save(dette);
    
                boolean update = articleService.update(art);
                if (!update) {
                    System.out.println("Erreur lors de la mise à jour de l'article dans la base de données.");
                }
                
            return dette ;
        } else {
            return null;
        }
        
        }
    }

    public Dette createDette() {

        Dette dette =new Dette();
        // scanner.nextLine();
        Client cl=UserConnect.getUserConnect().getClient() ;
    
            Details details= detailsView.create();
            if (details!=null) {
                dette.setTypeDette(TypeDette.nonSolde);
                details.setDette(dette);
                cl.setListeDette(dette); 
                dette.setClient(cl);          
                dette.setListeDetails(details); 
                Article art = details.getArticle();
                if (details.getQteDette() > art.getQteStock()) {
                    System.out.println("Erreur: La quantité demandée dépasse le stock disponible.");
                    return null;
                }
                dette.setMontant(details.getQteDette() * art.getPrix());
                art.setQteStock(art.getQteStock() - details.getQteDette());
                dette.setMontantVerse(0);
                dette.setMontantRestant(dette.getMontant() - dette.getMontantVerse());
                // detteService.save(dette);
    
                boolean update = articleService.update(art);
                if (!update) {
                    System.out.println("Erreur lors de la mise à jour de l'article dans la base de données.");
                }     

            return dette ; 
        }
        return null ;
    }

    public void afficherMesDettes() {
        Client cl = UserConnect.getUserConnect().getClient();
        
        if (cl != null) {
            if (cl.getListeDette().isEmpty()) {
                System.out.println("Vous n'avez plus de dette.");
            } else {
                System.out.println(detteService.getByType(TypeDette.nonSolde));
            }
        } else {
            System.err.println("Vous n'avez pas encore de dette..");
        }
    }
    

    public void listerDetteClient() {
        Client cl = clientView.askClient();
        if (cl == null) {
            return;
        }
    
        if (cl.getListeDette().isEmpty()) {
            System.out.println("Ce client n'a pas de dette ");
            return;
        }
        System.out.println("1- lister les dettes non soldées");
        System.out.println("2- lister les dettes soldées");
    
        int choice = choiceToContinue();
        TypeDette typeDette = (choice == 1) ? TypeDette.nonSolde : TypeDette.Solde;
    
        afficherDettes(typeDette);
    }
    
    private void afficherDettes(TypeDette type) {
        if (detteService.getByType(type) == null) {
            System.out.println("Ce client n'a pas de dettes " + (type == TypeDette.nonSolde ? "non soldées" : "soldées"));
        } else {
            System.out.println(detteService.getByType(type));
        }
    }
    

    public void archiverDetteSolde(){
        Client cl=clientView.askClient();
        if (cl!=null) {
            if (detteService.getByType(TypeDette.Solde)==null) {
                System.out.println("il n'y a pas de dette soldées");
            } else {
            List<Dette> dettes = detteService.getByType(TypeDette.Solde);
            for (Dette dette : dettes) {
                if (dette.isArchiver()) {
                    System.out.println("dettes soldées sont deja archiveées");  
                }else{
                    dette.setArchiver(true);
                    boolean update = detteService.update(dette);
                    if (update) {
                        System.out.println("ces dette solde sont archivées avec succes");
                    } else {
                        System.out.println("Erreur lors de l'archivage de la dette.");
                    }
                } 
            } 
        } 
        } else {
            System.out.println("pas de client");
        } 
    }

    // public void archiverDetteSolde() {
    //     Client cl = clientView.askClient();
    //     if (cl != null) {
    //         List<Dette> dettesSoldees = detteService.getByType(TypeDette.Solde);
    //         if (dettesSoldees == null || dettesSoldees.isEmpty()) {
    //             System.out.println("Il n'y a pas de dettes soldées.");
    //         } else {
    //             boolean auMoinsUneDetteArchivée = false;
    //             for (Dette dette : dettesSoldees) {
    //                 if (!dette.isArchiver()) {
    //                     dette.setArchiver(true);
    //                     boolean update = detteService.update(dette);
    //                     if (update) {
    //                         System.out.println("La dette ID " + dette.getId() + " a été archivée avec succès.");
    //                         auMoinsUneDetteArchivée = true;
    //                     } else {
    //                         System.out.println("Erreur lors de l'archivage de la dette ID " + dette.getId() + ".");
    //                     }
    //                 } else {
    //                     System.out.println("La dette ID " + dette.getId() + " est déjà archivée.");
    //                 }
    //             }
    //             if (!auMoinsUneDetteArchivée) {
    //                 System.out.println("Toutes les dettes soldées étaient déjà archivées.");
    //             }
    //         }
    //     } else {
    //         System.out.println("Aucun client sélectionné.");
    //     }
    // }
    

    public Dette askDette() {
        if (detteService.show().isEmpty()) {
            System.out.println("Il n'y a pas encore de dette. Veuillez en créer un d'abord.");
            return null;
        } else {
            Dette dette = null;
            do {
                System.out.println(detteService.getByType(TypeDette.nonSolde));
                System.out.println("Veuillez choisir la dette en entrant son ID (ou entrez 0 pour revenir à la liste) :");
    
                if (scanner.hasNextInt()) {
                    int detteId = scanner.nextInt();
                    if (detteId == 0) {
                        System.out.println("Demande annulée");
                        return null;
                    }
                    dette = detteService.getById(detteId);

                    if (dette == null) {
                        System.out.println("Cette dette n'existe pas. Veuillez entrer un ID valide.");
                    }
                } else {
                    System.out.println("Veuillez entrer un ID valide.");
                    scanner.next(); 
                }
    
            } while (dette == null);
    
            return dette;
        }
    }
}
