package com.example.backend.repositories;

import com.example.backend.entites.Bookmark;
import com.example.backend.entites.Link;
import com.example.backend.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findByUserAndLink(User user, Link link);
}

