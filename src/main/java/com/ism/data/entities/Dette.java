package com.ism.data.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.hibernate.annotations.ColumnDefault;

import com.ism.data.enums.EtatDette;
import com.ism.data.enums.TypeDette;
@Data()
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name="dette")
@ToString(exclude = "client")
public class Dette extends AbstractEntity implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate date;

    public Dette() {
        date=LocalDate.now();
    }

    private double montant;

    @Column(name = "montant_verse")
    private double montantVerse;

    @Column(name = "montant_restant")
    private double montantRestant;
    
    @Enumerated(EnumType.STRING)
    private TypeDette typeDette;

    @Enumerated(EnumType.STRING)
    private EtatDette etatDette;


    @ColumnDefault(value = "true")
    private boolean archiver;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @OneToMany(mappedBy = "dette", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Details> listeDetails = new ArrayList<>();

    @OneToMany(mappedBy = "dette", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Paiement> listePaiements = new ArrayList<>();

    public void setListeDetails(Details details) {
        details.setDette(this); // Associer le détail à cette dette
        listeDetails.add(details);    
    }
  
    public void setListeDetails(List<Details> details) {
        this.listeDetails.clear();  // On efface d'abord l'ancienne liste
        if (details != null) {
            for (Details detail : details) {
                detail.setDette(this);  // Assurez-vous que chaque détail est bien lié à cette dette
                this.listeDetails.add(detail);
            }
        }
    }
    
    public void setListePaiement(Paiement paiement) {
        this.montantRestant=this.montantRestant-this.montantVerse;
        if(this.montantVerse==this.montant){
            this.montantRestant=0;
            this.typeDette=TypeDette.Solde;
        }else{
            this.typeDette=TypeDette.nonSolde;
        }
        listePaiements.add(paiement);
    }

    public void setListePaiement(List<Paiement> paiements) {
        this.listePaiements.clear(); 
    
        if (paiements != null) {
            for (Paiement paiement : paiements) {
                paiement.setDette(this);
                this.listePaiements.add(paiement);
            }
            this.montantRestant = this.montant - this.montantVerse;
            
            if (this.montantRestant <= 0) {
                this.montantRestant = 0;
                this.typeDette = TypeDette.Solde;  
            } else {
                this.typeDette = TypeDette.nonSolde; 
            }
        }
    }
    
    

}
