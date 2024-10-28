package com.ism.data.entities;
import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name="paiement")
// @ToString(exclude = "dette")
public class Paiement extends AbstractEntity implements Identifiable  {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    public Paiement() {
        date=LocalDate.now();
    } 


    private LocalDate date;
    
    @Column(name = "montant")
    private double montant;
    
    @ManyToOne
    @JoinColumn(name = "dette_id", referencedColumnName = "id")
    private Dette dette;

    @Override
    public String toString() {
        return "Paiement [id=" + id + ", date=" + date + ", montant=" + montant + ", dette=" + dette.getId() + "]";
    }


}
