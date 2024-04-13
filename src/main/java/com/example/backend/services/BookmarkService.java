package com.example.backend.services;

import com.example.backend.dtos.UserDto;
import com.example.backend.entites.Bookmark;
import com.example.backend.entites.Link;
import com.example.backend.entites.User;
import com.example.backend.mappers.BookmarkMapper;
import com.example.backend.repositories.BookmarkRepository;
import com.example.backend.repositories.LinkRepository;
import com.example.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;

    private final LinkRepository linkRepository;

    private final UserRepository userRepository;


    public void saveBookmark(Long linkId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDto userDto) {
            User user = userRepository.findByLogin(userDto.getLogin()).get();

            Link link = linkRepository.findById(linkId).get();

            if (Objects.isNull(link)) {
                throw new RuntimeException("Link not found");
            }

            Bookmark bookmark = new Bookmark();
            bookmark.setUser(user);
            bookmark.setLink(link);

            bookmarkRepository.save(bookmark);
        }
    }

    public void deleteBookmark(Long linkId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDto userDto) {
            User user = userRepository.findByLogin(userDto.getLogin()).get();

            Link link = linkRepository.findById(linkId).get
                    ();

            if (Objects.isNull(link)) {
                throw new RuntimeException("Link not found");
            }

            Bookmark bookmark = bookmarkRepository.findByUserAndLink(user, link).get();
            bookmarkRepository.delete(bookmark);
        }

    }

}
