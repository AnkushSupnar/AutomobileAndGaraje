package com.ankush.data.repositories;

import com.ankush.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select  username from User")
    List<String>getAllUsernames();

    User getByUsername(@Param("username") String username);
}