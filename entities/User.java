package com.its.library.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int Id;
    
    public String Name;
    public String LastName;

    // 'mappedBy' si riferisce al nome del campo nella classe Book, cioè 'user'
       @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    public List<Book> books;
   
}
