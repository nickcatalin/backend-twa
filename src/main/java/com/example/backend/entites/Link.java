package com.example.backend.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "link")
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // This line is key
    private Long id;
    private String url;
    private String title;
    private String description;
    private String creation_date;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "link")
    private List<Comment> comments = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "link")
    private List<Bookmark> bookmarks = new ArrayList<>();
}
