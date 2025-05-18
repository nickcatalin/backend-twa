package com.example.backend.repositories;

import com.example.backend.entites.Link;
import com.example.backend.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {

    List<Link> findByUser(User user);

    void deleteByUserId(Long id);

    @Query("SELECT SUBSTRING(l.url, LOCATE('://', l.url) + 3, CASE WHEN LOCATE('/', l.url, LOCATE('://', l.url) + 3) > 0 THEN LOCATE('/', l.url, LOCATE('://', l.url) + 3) - LOCATE('://', l.url) - 3 ELSE LENGTH(l.url) - LOCATE('://', l.url) - 3 END) AS domain, COUNT(l) AS count FROM Link l GROUP BY domain")
    List<Object[]> getLinksByDomain();
}
