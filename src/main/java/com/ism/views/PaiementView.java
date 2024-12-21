package com.ism.views;

import java.util.Scanner;
import com.ism.core.view.ViewImpl;
import com.ism.data.entities.Client;
import com.ism.data.entities.Dette;
import com.ism.data.entities.Paiement;
import com.ism.data.enums.TypeDette;
import com.ism.data.services.list.DetteService;
import com.ism.data.services.list.UserConnect;

public class PaiementView extends ViewImpl<Paiement> {

    private DetteView detteView;
    private ClientView clientView;
    private DetteService detteservice ;

    public PaiementView(Scanner scanner, DetteView detteView, ClientView clientView,DetteService detteservice ) {
        super(scanner);
        this.detteView = detteView;
        this.clientView = clientView;
        this.detteservice = detteservice;

    }

    @Override
    public Paiement create() {
        Paiement pmt=new Paiement();
        Client cl=clientView.askClient();
        if (cl==null) {
            return null;
        } else {
            Dette dette = detteView.askDette();
            if (dette==null) {
                System.out.println("ce client na pas de dette");
                return null;
            }else{
                int m;
                do {
                    System.out.println("entrer le montant  paiyer");
                    m=scanner.nextInt();
                } while ( m<=0 || m > dette.getMontantRestant());
                dette.setMontantVerse(dette.getMontantVerse()+m);
                pmt.setMontant(m);
                // dette.setMontantRestant(dette.getMontantRestant()-pmt.getMontant());
                dette.setListePaiement(pmt);
                pmt.setDette(dette); 
                
                if (dette.getMontantRestant() == 0 && dette.getMontantVerse()==dette.getMontant()) {
                    System.out.println("La dette est entièrement remboursée.");
                    dette.setTypeDette(TypeDette.Solde);  
                }

                boolean update = detteservice.update(dette);
                if (update) {
                    System.out.println("Paiement et mise à jour de la dette réussis.");
                } else {
                    System.out.println("Erreur lors de la mise à jour de la dette.");
                }

            return pmt;
            } 
        }
    } 


    // public Paiement createPaiement() {
    //     Paiement pmt=new Paiement();
    //     Client cl=UserConnect.getUserConnect().getClient();
        
    //         Dette dette = detteView.askDette();
    //         if (dette==null) {
    //             System.out.println("ce client na pas de dette");
    //             return null;
    //         }else{
    //             int m;
    //             do {
    //                 System.out.println("entrer le montant  paiyer");
    //                 m=scanner.nextInt();
    //             } while ( m<=0 || m > dette.getMontantRestant());
    //             dette.setMontantVerse(dette.getMontantVerse()+m);
    //             pmt.setMontant(m);
    //             // dette.setMontantRestant(dette.getMontantRestant()-pmt.getMontant());
    //             dette.setListePaiement(pmt);
    //             pmt.setDette(dette); 
                
    //             if (dette.getMontantRestant() == 0 && dette.getMontantVerse()==dette.getMontant()) {
    //                 System.out.println("La dette est entièrement remboursée.");
    //                 dette.setTypeDette(TypeDette.Solde);  
    //             }

    //             boolean update = detteservice.update(dette);
    //             if (update) {
    //                 System.out.println("Paiement et mise à jour de la dette réussis.");
    //             } else {
    //                 System.out.println("Erreur lors de la mise à jour de la dette.");
    //             }

    //             return pmt;
    //         } 
    // } 
}
