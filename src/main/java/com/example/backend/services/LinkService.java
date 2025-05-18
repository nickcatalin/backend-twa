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

import java.util.*;
import java.util.stream.Collectors;

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

    public long getTotalLinks() {
        return linkRepository.count();
    }

    public Map<String, Long> getLinkCountByDomain() {
        List<Object[]> results = linkRepository.getLinksByDomain();
        Map<String, Long> domainCounts = new java.util.HashMap<>();
        
        for (Object[] result : results) {
            String domain = (String) result[0];
            Long count = ((Number) result[1]).longValue(); // Convert to Long regardless of the actual number type
            domainCounts.put(domain, count);
        }
        
        return domainCounts;
    }

    public Map<String, Long> getTopCommentedLinks(int limit) {
        List<Link> allLinks = linkRepository.findAll();
        Map<String, Long> commentCounts = new HashMap<>();
        
        // Count comments for each link
        for (Link link : allLinks) {
            int commentCount = link.getComments().size();
            if (commentCount > 0) {
                commentCounts.put(link.getTitle(), (long) commentCount);
            }
        }
        
        // Sort by comment count in descending order and limit the results
        return commentCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(limit)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }
}
