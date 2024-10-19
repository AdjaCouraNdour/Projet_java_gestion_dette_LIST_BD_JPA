package com.ism.data.entities;
import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name="paiement")
@ToString(exclude = "dette")
public class Paiement extends AbstractEntity implements Identifiable  {
   
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Transient
    private static int cpt = 1 ;

    public Paiement() {
        id=++cpt;
        date=LocalDate.now();
    } 

    private LocalDate date;
    
    @Column(name = "montant")
    private double montant;
    
    @ManyToOne
    @JoinColumn(name = "dette_id", referencedColumnName = "id")
    private Dette dette;


}
