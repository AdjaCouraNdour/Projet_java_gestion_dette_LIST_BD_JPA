package com.ism.data.entities;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.annotations.ColumnDefault;

import com.ism.data.enums.UserRole;

@Data
@EqualsAndHashCode(callSuper = false ,of={"login"})
@Entity
@Table(name="user")
@NamedQueries({
   @NamedQuery(name = "selectByLogin",query = "a remplir ")
})
public class User extends AbstractEntity implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Transient
    private static int cpt = 1 ;

    public User() {
        this.id = cpt++; 
    }
    
    @Column(length = 25,unique = true)
    private String email;

    @Column(length = 25,unique = true)
    private String login;
 
    @Column(length = 10,unique = true)
    private String password;

    @Enumerated(EnumType.ORDINAL)
    private UserRole userRole;
    
    @ColumnDefault(value = "'true'")
    @Column(nullable = false)
    private boolean actif = true;
        
    @OneToOne(mappedBy = "client" ,fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "client_id", nullable = true)
    private Client client;

   

}
