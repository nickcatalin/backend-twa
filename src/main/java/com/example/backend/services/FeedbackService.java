package com.example.backend.services;

import com.example.backend.dtos.FeedbackDto;
import com.example.backend.dtos.UserDto;
import com.example.backend.entites.Comment;
import com.example.backend.entites.Feedback;
import com.example.backend.entites.Link;
import com.example.backend.entites.User;
import com.example.backend.mappers.FeedbackMapper;
import com.example.backend.repositories.FeedbackRepository;
import com.example.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    private final FeedbackMapper feedbackMapper;

    private final UserRepository userRepository;

    public void addFeedback(final FeedbackDto feedbackDto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDto userDto) {
            User user = userRepository.findByLogin(userDto.getLogin()).get();

            Feedback feedback = feedbackMapper.toFeedback(feedbackDto);
            feedback.setUser(user);

            feedbackRepository.save(feedback);
        }
    }

    public List<FeedbackDto> getFeedbacks() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDto userDto) {
            User user = userRepository.findByLogin(userDto.getLogin()).get();


            if (user.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"))) {
                return feedbackRepository.findAll().stream()
                        .map(feedbackMapper::toFeedbackDto)
                        .toList();
            }


            return feedbackRepository.findAllByIsPrivateTrueOrUserEquals(user).stream()
                    .map(feedbackMapper::toFeedbackDto)
                    .toList();


        }

        return List.of();
    }
}
