package com.ism.data.entities;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
// @ToString(includeFieldNames = true )
@EqualsAndHashCode(callSuper = false,of = {"telephone"})
@Entity
@Table(name="client")

public class Client extends AbstractEntity implements Identifiable {

    @Column(length = 20,unique = true)
    private String prenom;

    @Column(length = 20,unique = true)
    private String nom;

    @Column(length = 9,unique = true)
    private String telephone;

    @Column(length = 255,unique = true)
    private String address;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dette> listeDette = new ArrayList<>();


    public void setListeDette(Dette dette) {
        dette.setClient(this); 
        listeDette.add(dette);
    }


    @Override
    public String toString() {
        return "Client [" +"id="+ id + ",prenom=" + prenom + ", nom=" + nom + ", telephone=" + telephone + ", address=" + address
                +"]";
    }
    

}
