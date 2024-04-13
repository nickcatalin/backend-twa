package com.example.backend.mappers;

import com.example.backend.dtos.RatingDto;
import com.example.backend.entites.Rating;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RatingMapper {

    RatingDto toRatingDto(Rating rating);

    Rating toRating(RatingDto ratingDto);
}
