package com.its.library.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.its.library.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // public List<User> findAll();

  @Query(value="SELECT * FROM user WHERE name = :name AND last_name = :lastname", nativeQuery=true)
    public User  SeachByName(@Param("name") String name, @Param("lastname") String lastname);
}
