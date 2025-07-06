package com.its.library.dtos;
import com.its.library.entities.Book;

public class BookDto {
    public int Id;
    public String Title;
    public String Author;
    public int PublishYear;
    public String ClientName;
    public String ClientSurname;
    public int IdUser;
    // E' necessario definire un costruttore vuoto per garantire la traduzione
    // dell'oggetto in JSON
    public BookDto() {
    }

    public BookDto(Book book) {
        this.Id = book.Id;
        this.Author = book.Author;
        this.Title = book.Title;
        this.PublishYear = book.PublishYear;
        this.ClientName = book.ClientName;
        this.ClientSurname= book.ClientSurname;
    }
    
}
