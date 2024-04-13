package com.example.backend.services;

import com.example.backend.dtos.LinkDto;
import com.example.backend.dtos.UserDto;
import com.example.backend.entites.Link;
import com.example.backend.entites.User;
import com.example.backend.mappers.LinkMapper;
import com.example.backend.repositories.CommentRepository;
import com.example.backend.repositories.LinkRepository;
import com.example.backend.repositories.RatingRepository;
import com.example.backend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class LinkService {

    private final LinkRepository linkRepository;

    private final LinkMapper linkMapper;

    private final UserRepository userRepository;

    private final CommentRepository commentRepository;

    private final RatingRepository ratingRepository;


    public void saveLink(LinkDto linkDto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDto userDto) {

            User user = userRepository.findByLogin(userDto.getLogin()).get();
            Link link = linkMapper.toLink(linkDto);
            link.setUser(user);
            linkRepository.save(link);

        }

    }

    public List<LinkDto> getLinks() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDto userDto) {

            User user = userRepository.findByLogin(userDto.getLogin()).get();
            List<Link> links = linkRepository.findAll();
            List<LinkDto> linkDtos = new ArrayList<>();

            links.forEach(link -> {
                LinkDto linkDto = linkMapper.toLinkDto(link);
                linkDto.setBookmarked(link.getBookmarks().stream().anyMatch(bookmark -> bookmark.getUser().getId().equals(user.getId())));
                linkDtos.add(linkDto);
            });

            return linkDtos;
        }
        return List.of();
    }

    public void updateLink(Long id, LinkDto linkDto) {
        Optional<Link> existingLinkOptional = linkRepository.findById(id);
        if (existingLinkOptional.isPresent()) {
            Link existingLink = existingLinkOptional.get();
            existingLink.setTitle(linkDto.getTitle());
            existingLink.setUrl(linkDto.getUrl());
            existingLink.setDescription(linkDto.getDescription());
            // Save the updated link
            linkRepository.save(existingLink);
        } else {
            throw new RuntimeException("Link not found with id: " + id);
            // Consider using a more specific exception
        }
    }

    public void deleteLink(Long id) {
        if (linkRepository.existsById(id)) {
            commentRepository.deleteAllByLinkId(id);
            ratingRepository.deleteAllByLinkId(id);
            linkRepository.deleteById(id);
        } else {
            throw new RuntimeException("Link not found with id: " + id);
        }
    }

    public Link getLink(Long linkId) {
        return linkRepository.findById(linkId).orElseThrow(() -> new IllegalArgumentException("Link with id " + linkId + " not found"));
    }
}
