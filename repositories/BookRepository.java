package com.its.library.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.its.library.entities.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    // public List<Book> findAll();

    // public long countByUser_Id(int userId);
    @Query(value = "SELECT * FROM book WHERE iduser = 0 OR iduser IS NULL", nativeQuery = true)
    List<Book> findBooksWithIDUserZeroOrNull();

    @Query(value = "SELECT * FROM book WHERE iduser = :iduser", nativeQuery = true)
    List<Book> findBooksByIDUser(@Param("iduser") int iduser);

    @Query(value = "SELECT COUNT(*) FROM book WHERE iduser = :id", nativeQuery = true)
    long countByUserId(@Param("id") int id);

    @Modifying
    @Query(value = "UPDATE book SET iduser = 0 WHERE id = :id", nativeQuery = true)
    void unsetUser(@Param("id") int bookId);
    
}
