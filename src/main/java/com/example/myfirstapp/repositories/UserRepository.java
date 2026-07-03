package com.example.myfirstapp.repositories;

import com.example.myfirstapp.dtos.UserDetails;
import com.example.myfirstapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT u.id,u.name,ui.city FROM users as u join user_info as ui on u.id=ui.user_id", nativeQuery = true)
    List<UserDetails> findAllUsers();
}
