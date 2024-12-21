package com.ism.views;

import java.util.List;
import java.util.Scanner;

import com.ism.core.view.ViewImpl;
import com.ism.data.entities.Article;
import com.ism.data.entities.Client;
import com.ism.data.entities.Details;
import com.ism.data.entities.Dette;
import com.ism.data.enums.EtatDette;
import com.ism.data.enums.TypeDette;
import com.ism.data.services.list.ArticleService;
import com.ism.data.services.list.DetailsService;
import com.ism.data.services.list.DetteService;
import com.ism.data.services.list.UserConnect;


public class DetteView extends ViewImpl<Dette>{

    private DetailsView detailsView ;
    private DetailsService detailsService;
    private DetteService detteService;
    private ClientView clientView;
    private ArticleService articleService;
    
    public DetteView(Scanner scanner, DetailsView detailsView,
            DetteService detteService,ClientView clientView,ArticleService articleService,DetailsService detailsService) {
        super(scanner);
        this.detailsView = detailsView;
        this.detteService = detteService;
        this.clientView = clientView;
        this.articleService = articleService;
        this.detailsService = detailsService;

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
                dette.setEtatDette(EtatDette.EnCours);
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

    public Dette askDette() {
        if (detteService.show().isEmpty()) {
            System.out.println("Il n'y a pas encore de dette. Veuillez en créer un d'abord.");
            return null;
        } else {
            Dette dette = null;
            do {
                System.out.println(detteService.show());
                System.out.println("Veuillez choisir le dette en entrant son ID (ou entrez 0 pour revenir à la liste) :");
    
                if (scanner.hasNextInt()) {
                    int detteId = scanner.nextInt();
                    if (detteId == 0) {
                        System.out.println("Demande annulée");
                        return null;
                    }
                    dette = detteService.getById(detteId);

                    if (dette == null) {
                        System.out.println("Ce dette n'existe pas. Veuillez entrer un ID valide.");
                    }
                } else {
                    System.out.println("Veuillez entrer un ID valide.");
                    scanner.next(); 
                }
    
            } while (dette == null);
    
            return dette;
        }
    }
   
    // Fonction principale pour afficher les dettes en cours et gérer l'interaction utilisateur
    public void accepterOuRefuserDette() {
        List<Dette> dettesEnCours = detteService.getByEtat(EtatDette.EnCours);
        if (dettesEnCours == null || dettesEnCours.isEmpty()) {
            System.out.println("Il n'y a pas de dettes en cours.");
            return;
        }

        System.out.println("Dettes en cours :");
        for (int i = 0; i < dettesEnCours.size(); i++) {
            Dette dette = dettesEnCours.get(i);
            System.out.println((i + 1) + " - Dette ID: " + dette.getId() + ", Montant: " + dette.getMontant()
                    + ", Client: " + (dette.getClient() != null ? dette.getClient().getNom() : "Inconnu"));
        }

        System.out.println("Veuillez sélectionner une dette (par numéro) ou 0 pour annuler :");
        int choix = scanner.nextInt();
        if (choix == 0) {
            System.out.println("Opération annulée.");
        }
        if (choix < 1 || choix > dettesEnCours.size()) {
            System.out.println("Choix invalide.");
        }

        Dette detteChoisie = detteService.getById(dettesEnCours.get(choix - 1).getId());
        System.out.println("1 - Accepter la dette.");
        System.out.println("2 - Refuser la dette.");
        int decision = scanner.nextInt();

        switch (decision) {
            case 1->{
                acceptDette(detteChoisie);
            }
            case 2->{
                refuseDette(detteChoisie);
            }

        }
    }

    private Dette acceptDette(Dette dette) {
        dette.setEtatDette(EtatDette.Accepter);
        if (detteService.update(dette)) {
            System.out.println("La dette ID " + dette.getId() + " a été acceptée.");
        } else {
            System.out.println("Erreur lors de la mise à jour de la dette ID " + dette.getId() + ".");
        }
        return dette;
    }

    private void refuseDette(Dette dette) {
        dette.setEtatDette(EtatDette.Refuser);
        if (detteService.update(dette)) {
            List<Details> detailsList = dette.getListeDetails();
            if (detailsList != null && !detailsList.isEmpty()) {
                for (Details detail : detailsList) {
                    Article article = detail.getArticle();
                    if (article != null) {
                        article.setQteStock(article.getQteStock() + detail.getQteDette());
                        if (articleService.mettreAJour(article)) {
                            System.out.println("La quantité de l'article ID " + article.getId() + " a été mise à jour.");
                        } else {
                            System.out.println("Erreur lors de la mise à jour de l'article ID " + article.getId() + ".");
                        }
                    } else {
                        System.out.println("Aucun article associé trouvé pour le détail ID " + detail.getId() + ".");
                    }
                }
                System.out.println("Tous les détails associés ont été traités pour la dette ID " + dette.getId() + ".");
            } else {
                System.out.println("Aucun détail trouvé pour la dette ID " + dette.getId() + ".");
            }
            System.out.println("La dette ID " + dette.getId() + " a été refusée.");
        } else {
            System.out.println("Erreur lors de la mise à jour de la dette ID " + dette.getId() + ".");
        }
    }

    public void afficherMesDettesRefuser() {
        List<Dette> dette = detteService.getByEtat(EtatDette.Refuser);
        if (dette == null) {
            System.out.println("Ce client n'a pas de dettes refuser");
        } else {
            for (int i = 0; i < dette.size(); i++) {
                System.out.println((i + 1) + " - Dette ID: " + dette.get(i).getId() + ", Montant: " + dette.get(i).getMontant()
                + ", Client: " + (dette.get(i).getClient() != null ? dette.get(i).getClient().getNom() : "Inconnu"));
            }
            System.out.println("vouler vous relancer une dette");
            System.out.println("1 - Oui");
            System.out.println("2 - Non");
            int choix = scanner.nextInt();
            if (choix == 1) {
                relancerDette();
            }else if (choix == 2) {
                System.out.println("Opération annulée.");
            }
        }
    } 


    private void relancerDette() {
        System.out.println("Veuillez sélectionner une dette (par numéro) ou 0 pour annuler :");
        int choix = scanner.nextInt();
        if (choix == 0) {
            System.out.println("Opération annulée.");
        }
        Dette dette = detteService.getById(choix);
        if (dette == null || dette.getEtatDette() != EtatDette.Refuser) {
            System.out.println("Choix invalide ou aucune dette refusée trouvée pour ce client.");
        }
        dette.setEtatDette(EtatDette.EnCours);
        if (detteService.update(dette)) {
            List<Details> detailsList = dette.getListeDetails();
            if (detailsList != null && !detailsList.isEmpty()) {
                for (Details detail : detailsList) {
                    Article article = detail.getArticle();
                    if (article != null) {
                        article.setQteStock(article.getQteStock() - detail.getQteDette());
                        if (articleService.mettreAJour(article)) {
                            System.out.println("La quantité de l'article ID " + article.getId() + " a été mise à jour.");
                           
                        } else {
                            System.out.println("Erreur lors de la mise à jour de l'article ID " + article.getId() + ".");
                        }
                    } else { 
                        System.out.println("Aucun article associé rencontré pour le détail ID " + detail.getId() + ".");
                    }
                }
            } else {
                System.out.println("Aucun détail associé trouvé pour la dette ID " + dette.getId() + ".");
            }
            System.out.println("La dette ID " + dette.getId() + " a été relancée.");
        } else {
            System.out.println("Erreur lors de la mise à jour de la dette ID " + dette.getId() + ".");
        }
    }    
}
