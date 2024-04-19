package com.example.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedbackDto {
    private Long id;
    private String questionAnswer;
    private Integer stars;
    private Boolean isPrivate;
    private String content;
    private String creation_date;
    private UserDto user;
}
