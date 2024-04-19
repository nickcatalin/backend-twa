package com.example.backend.mappers;

import com.example.backend.dtos.FeedbackDto;
import com.example.backend.entites.Feedback;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {

     FeedbackDto toFeedbackDto(Feedback feedback);

     Feedback toFeedback(FeedbackDto feedbackDto);

}
