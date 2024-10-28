package com.ism.data.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
@MappedSuperclass
public class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "create_at")
    private LocalDateTime createAt;
    @Column(name = "update_at")
    private LocalDateTime updateAt;
   
    @PrePersist
    public void onPrePersist(){
        this.setCreateAt(LocalDateTime.now());
        this.setUpdateAt(LocalDateTime.now());
    } 
    @PreUpdate
    public void onPreUpdate(){
        this.setUpdateAt(LocalDateTime.now());
    } 
    
    // private User userCreate;
    // private User userUpdate;

}
