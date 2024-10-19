package com.ism.views;

import java.util.Scanner;
import com.ism.core.view.ViewImpl;
import com.ism.data.entities.Client;
import com.ism.data.entities.Dette;
import com.ism.data.entities.Paiement;

public class PaiementView extends ViewImpl<Paiement> {

    private DetteView detteView;
    private ClientView clientView;

    public PaiementView(Scanner scanner, DetteView detteView, ClientView clientView) {
        super(scanner);
        this.detteView = detteView;
        this.clientView = clientView;
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
            return pmt;
            } 
        }
    } 
}
