package com.example.backend.dtos;

import com.example.backend.entites.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEditDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String login;
    private Set<Role> roles = new HashSet<>();
    private List<LinkDto> links = new ArrayList<>();
    private List<CommentDto> comments = new ArrayList<>();
    private List<RatingDto> ratings = new ArrayList<>();
    private List<BookmarkDto> bookmarks = new ArrayList<>();
}
