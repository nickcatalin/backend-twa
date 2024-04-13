package com.example.backend.services;

import com.example.backend.dtos.RatingDto;
import com.example.backend.dtos.UserDto;
import com.example.backend.entites.Link;
import com.example.backend.entites.Rating;
import com.example.backend.entites.User;
import com.example.backend.mappers.RatingMapper;
import com.example.backend.repositories.RatingRepository;
import com.example.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RatingService {
    private final RatingRepository ratingRepository;
    private final RatingMapper ratingMapper;
    private final LinkService linkService;
    private final UserRepository userRepository;

    public void saveRating(RatingDto ratingDto, Long linkId) {


        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDto userDto) {
            User user = userRepository.findByLogin(userDto.getLogin()).get();

            Link link = linkService.getLink(linkId);

            if (Objects.isNull(link)) {
                throw new RuntimeException("Link not found");
            }

            Rating rating = ratingMapper.toRating(ratingDto);

            rating.setUser(user);
            rating.setLink(link);

            ratingRepository.save(rating);

        }
    }

    public RatingDto getRating(Long linkId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDto userDto) {
            User user = userRepository.findByLogin(userDto.getLogin()).get();

            Link link = linkService.getLink(linkId);

            if (Objects.isNull(link)) {
                throw new RuntimeException("Link not found");
            }

            Optional<Rating> rating = ratingRepository.findByLinkAndUser(link, user);
            if (rating.isPresent()) {
                return ratingMapper.toRatingDto(rating.get());
            }
        }
        return null;
    }

    public Long getAverageRating(Long linkId) {

        Link link = linkService.getLink(linkId);


        List<Rating> ratings = ratingRepository.findAllByLink(link);

        if (ratings.isEmpty()) {
            return 0L;
        }

        long sum = ratings.stream().mapToLong(Rating::getRating).sum();
        return sum * 20 / ratings.size();
    }
}
