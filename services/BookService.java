package com.its.library.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.its.library.dtos.BookDto;
import com.its.library.entities.Book;
import com.its.library.entities.User;
import com.its.library.repositories.BookRepository;
import com.its.library.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service

public class BookService {

    @Autowired
    private BookRepository booksrepository;
    @Autowired
    private UserRepository usersrepository;

    public List<BookDto> GetBooks() {
        List<Book> books = booksrepository.findAll();
        List<BookDto> dtos = new ArrayList<BookDto>();
        for (Book book : books) {
            BookDto dto = new BookDto(book);
            dtos.add(dto);
        }
        return dtos;

    }

    public List<BookDto> GetAvaibleBooks() {
        List<Book> books = booksrepository.findBooksWithIDUserZeroOrNull();
        List<BookDto> dtos = new ArrayList<BookDto>();
        for (Book book : books) {
            BookDto dto = new BookDto(book);
            dtos.add(dto);
        }
        return dtos;

    }

    public BookDto GetBook(int id) {
        Book book = booksrepository.findById(id).orElse(null);
        if (book == null) {
            return null;
        }
        return new BookDto(book);
    }

    public long CoutBook(int id) {
        long countbooks = booksrepository.countByUserId(id);
        return countbooks;
    }

    @Transactional
    public BookDto UpdateUserBook(BookDto dto) {
        Book book = booksrepository.findById(dto.Id).orElse(null);
        if (book == null) {
            return null;
        }

        book.Title = dto.Title;
        book.Author = dto.Author;
        book.PublishYear = dto.PublishYear;
        book.ClientName = dto.ClientName;
        book.ClientSurname = dto.ClientSurname;

        User user = usersrepository.findById(dto.IdUser).orElse(null);
        if (user == null) {
            return null;
        }

        book.user = user; // imposta la relazione JPA
        booksrepository.save(book);

        return new BookDto(book);
    }

    @Transactional
    public BookDto DeleteUserBook(BookDto dto) {
        Book book = booksrepository.findById(dto.Id).orElse(null);
        if (book == null) {
            return null;
        }

        booksrepository.unsetUser(book.Id); // forza iduser = 0
        return new BookDto(book);
    }

    public List<BookDto> ListBooksByIDUser(int iduser) {
        List<Book> listBook = booksrepository.findBooksByIDUser(iduser);
        List<BookDto> dtos = new ArrayList<BookDto>();
        for (Book book : listBook) {
            BookDto dto = new BookDto(book);
            dtos.add(dto);
        }
        return dtos;
    }

}
