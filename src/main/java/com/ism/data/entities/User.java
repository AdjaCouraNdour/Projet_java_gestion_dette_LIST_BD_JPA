package com.ism.data.entities;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.hibernate.annotations.ColumnDefault;

import com.ism.data.enums.UserRole;

@Data
@EqualsAndHashCode(callSuper = false ,of={"login"})
@Entity
@ToString(exclude = "client")
@Table(name="\"user\"")
@NamedQueries({
   @NamedQuery(name = "selectByLogin",query = "SELECT u FROM User u WHERE u.login = :login")
})
public class User extends AbstractEntity implements Identifiable {
    
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
        
    @OneToOne(mappedBy = "user" ,fetch = FetchType.EAGER )
    @JoinColumn(name = "client_id", nullable = true)
    private Client client;

    public User(String email, String login, String password, UserRole userRole, boolean actif) {
        this.email = email;
        this.login = login;
        this.password = password;
        this.userRole = userRole;
        this.actif = actif;
    }

    public User() {
    }


   

}
