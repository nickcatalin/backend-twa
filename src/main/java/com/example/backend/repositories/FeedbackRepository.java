package com.example.backend.repositories;

import com.example.backend.entites.Feedback;
import com.example.backend.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface FeedbackRepository extends JpaRepository<Feedback, Long> {



    @Query("SELECT f FROM Feedback f WHERE f.isPrivate = false OR f.user = ?1")
    List<Feedback> findAllByIsPrivateTrueOrUserEquals(User user);


}
