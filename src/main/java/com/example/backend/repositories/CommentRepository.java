package com.example.backend.repositories;

import com.example.backend.entites.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByLinkId(Long linkId);

    void deleteByUserId(Long id);

    void deleteAllByLinkId(Long id);
}
