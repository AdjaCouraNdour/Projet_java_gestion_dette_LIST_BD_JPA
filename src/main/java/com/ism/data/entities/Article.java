package com.ism.data.entities;

import java.util.ArrayList;
import java.util.List;

import com.ism.data.enums.EtatArticle;

import lombok.Data;
import lombok.EqualsAndHashCode;
// import lombok.ToString;
import jakarta.persistence.*;

@Data
@EqualsAndHashCode(callSuper = false, of={"reference","libelle"} )
@Entity
@Table(name="article")
// @ToString(exclude = "listeDetails")

public class Article extends AbstractEntity implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Transient
    private static int cpt = 1 ;
 
    public Article() {
        id=++cpt;
        this.reference = String.format("A%06d", id);
    }
   
    @Column(length = 10,unique = true)
    private String reference;
    
    @Column(length = 10,unique = true)
    private String libelle;

    private int prix;
    private double qteStock;

    @Enumerated(EnumType.STRING)
    private EtatArticle etatArticle;
    
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Details> listeDetails = new ArrayList<>();

    public void setListeDetails(Details details) {
        this.qteStock=this.qteStock-details.getQteDette();
        if(this.qteStock==0){
            this.etatArticle=EtatArticle.Indisponible;
        }else{
            this.etatArticle=EtatArticle.Disponible;  
        }
        listeDetails.add(details);

    }
    public void setQteStock(double qteStock) {
        this.qteStock = qteStock;
        if (this.qteStock == 0) {
            this.etatArticle = EtatArticle.Indisponible;
        } else {
            this.etatArticle = EtatArticle.Disponible;
        }    
    }
    @Override
    public String toString() {
        return "Article{" +
                "id=" + getId() + 
                ", reference='" + reference + 
                ", libelle='" + libelle + 
                ", prix=" + prix +
                ", qteStock=" + qteStock +
                ", etatArticle=" + etatArticle +
                '}';
    }



}
