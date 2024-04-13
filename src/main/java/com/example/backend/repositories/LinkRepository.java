package com.example.backend.repositories;

import com.example.backend.entites.Link;
import com.example.backend.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {

    List<Link> findByUser(User user);

    void deleteByUserId(Long id);
}
