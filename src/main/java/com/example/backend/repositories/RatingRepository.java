package com.example.backend.repositories;

import com.example.backend.entites.Link;
import com.example.backend.entites.Rating;
import com.example.backend.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    Optional<Rating> findByLinkAndUser(Link link, User user);

    List<Rating> findAllByLink(Link link);

    void deleteByUserId(Long id);

    void deleteAllByLinkId(Long id);
}
