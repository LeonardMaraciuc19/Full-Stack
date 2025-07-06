package com.its.library.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.its.library.dtos.BookDto;
import com.its.library.dtos.BookRequestDto;
import com.its.library.dtos.UserDto;
import com.its.library.dtos.UserRequestDto;
import com.its.library.entities.User;
import com.its.library.services.BookService;
import com.its.library.services.UserService;

@RestController
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;

    @GetMapping("get-books")
    public List<BookDto> GetBooks() {
        List<BookDto> books = bookService.GetBooks();
        return books;

    }

    @GetMapping("get-avaiability-books")
    public List<BookDto> GetAvaibleBooks() {
        List<BookDto> books = bookService.GetAvaibleBooks();
        return books;

    }

    @PostMapping("/loan-book")
    public ResponseEntity<Map<String, String>> loanBook(@RequestBody BookRequestDto request) {
        String name = request.firstName;
        String lastName = request.lastName;
        int bookId = request.bookId;

        BookDto bookdto = bookService.GetBook(bookId);
        if (bookdto == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Libro non trovato"));
        }

        UserDto userdto = userService.GetUserByName(name, lastName);
        User user = null;

        if (userdto == null) {
            user = new User();
            user.Name = name;
            user.LastName = lastName;
            user = userService.AddUser(user); // salva e recupera con id
            userdto = new UserDto(user);
        }

        long count = bookService.CoutBook(userdto.Id);
        if (count >= 3) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Hai già 3 libri in prestito"));
        }

        // aggiorna libro assegnando l'utente
        bookdto.IdUser = userdto.Id;
        BookDto bookupdate = bookService.UpdateUserBook(bookdto);

        if (bookupdate == null) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Errore durante l'aggiornamento del libro"));
        }

        return ResponseEntity.ok(Map.of("message", "Prestito effettuato con successo"));
    }

    @PostMapping("/get-books-for-user")
    public ResponseEntity<?> getBooksForUser(@RequestBody UserRequestDto request) {
        String firstName = request.firstName;
        String lastName = request.lastName;
        UserDto userdto = userService.GetUserByName(firstName, lastName);

        if (userdto == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Utente non trovato"));
        }

        List<BookDto> books = bookService.ListBooksByIDUser(userdto.Id);
        if (books == null || books.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Nessun libro trovato per questo utente"));
        }

        return ResponseEntity.ok(books); // Restituisco la lista dei libri
    }

    @PostMapping("/return-book")
    public ResponseEntity<Map<String, String>> returnBook(@RequestBody BookRequestDto request) {
        int bookId = request.bookId;
        BookDto bookdto = bookService.GetBook(bookId);
        if (bookdto == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Libro non trovato"));
        }
        BookDto bookupdate = bookService.DeleteUserBook(bookdto);
        if (bookupdate == null) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Errore durante l'aggiornamento del libro"));
        }

        return ResponseEntity.ok(Map.of("message", "Prestito effettuato con successo"));
    }

}