package com.its.library.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int Id;

    public String Title;
    public String Author;
    public int PublishYear;
    public String ClientName;
    public String ClientSurname;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "iduser") // nome della colonna nella tabella Book
    public User user;
}
