package com.example.myfirstapp.repositories.test;

import com.example.myfirstapp.dtos.UserDetails;
import com.example.myfirstapp.models.test.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT u.id,u.name,ui.city FROM users as u join user_info as ui on u.id=ui.user_id order by u.id asc", nativeQuery = true)
    Page<UserDetails> findAllUsers(Pageable pageable);

    @Query(value = "SELECT u.id,u.name,ui.city FROM users as u join user_info as ui on u.id=ui.user_id where u.id = :id", nativeQuery = true)
    UserDetails findUserInfo(@Param("id") Integer id);
}
