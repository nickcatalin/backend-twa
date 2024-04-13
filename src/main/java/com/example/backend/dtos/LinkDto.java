package com.example.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LinkDto {
    private Long id;
    private String url;
    private String title;
    private String description;
    private String creation_date;
    private UserDto user;
    private boolean isBookmarked;
}
