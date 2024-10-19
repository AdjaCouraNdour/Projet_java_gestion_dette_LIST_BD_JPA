package com.ism.views;

import java.util.Scanner;

import com.ism.core.view.ViewImpl;
import com.ism.data.entities.Article;
import com.ism.data.entities.Client;
import com.ism.data.entities.Details;
import com.ism.data.entities.Dette;
import com.ism.data.enums.TypeDette;
import com.ism.data.services.list.DetteService;


public class DetteView extends ViewImpl<Dette>{

    private DetailsView detailsView ;
    private DetteService detteService;
    private ClientView clientView;
    
    public DetteView(Scanner scanner, DetailsView detailsView,
            DetteService detteService,ClientView clientView) {
        super(scanner);
        this.detailsView = detailsView;
        this.detteService = detteService;
        this.clientView = clientView;
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
                Article art=details.getArticle();
                dette.setMontant(details.getQteDette()*art.getPrix());
                art.setQteStock(art.getQteStock()- details.getQteDette());
                dette.setMontantVerse(0);
                dette.setMontantRestant(dette.getMontant()-dette.getMontantVerse());
            return dette ;
        } else {
            return null;
        }
        
        }
    }

    // public void listerDetteClient(){
    //     Client cl=clientView.askClient();
    //     if (cl==null) {
    //         return;
    //     } else {
    //         if (cl.getListeDette().isEmpty()) {
    //             System.out.println("Ce client n'as pas de dette ");
    //             return;  
    //         } else{
    //             System.out.println("1- lister les dettes non soldées");
    //             System.out.println("2- lister les dettes soldées");
    //             if (choiceToContinue()==1) {
    //                 if (detteService.getBy(TypeDette.nonSolde)==null) {
    //                     System.out.println("ce client n'a pas de dettes nonSoldées");
    //                 } else {
    //                     System.out.println(detteService.getBy(TypeDette.nonSolde));
    //                 }
    //             } else {
    //                 if (detteService.getBy(TypeDette.Solde)==null) {
    //                     System.out.println("ce client n'a pas de dettes Soldées");
    //                 } else {
    //                     System.out.println(detteService.getBy(TypeDette.Solde));
    //                 }
    //             }
    //         }
    //     }
     
    // }

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
    

    public Dette archiverDetteSolde(){
        Client cl=clientView.askClient();
        if (cl!=null) {
            if (detteService.getByType(TypeDette.Solde)==null) {
                System.out.println("il n'y a pas de dette soldées");
                return null;
            } else {
                // List<Dette>
               Dette dette =detteService.getBy(TypeDette.Solde);
                if (dette.isArchiver()) {
                    System.out.println("les dettes soldées sont deja archiveées");  
                }else{
                    dette.setArchiver(true);
                    System.out.println("ces dette solde sont archivées avec succes");
                } 
                return dette; 
            }  
        } else {
            return null;
        } 
    }

    public Dette askDette() {
        // Vérifier s'il existe des clients
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
