package com.example.backend.services;

import com.example.backend.dtos.CommentDto;
import com.example.backend.dtos.UserDto;
import com.example.backend.entites.Comment;
import com.example.backend.entites.Link;
import com.example.backend.entites.User;
import com.example.backend.mappers.CommentMapper;
import com.example.backend.repositories.CommentRepository;
import com.example.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final LinkService linkService;
    private final UserRepository userRepository;


    public void saveComment(CommentDto commentDto, Long linkId) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDto userDto) {
            User user = userRepository.findByLogin(userDto.getLogin()).get();

            Link link = linkService.getLink(linkId);

            if (Objects.isNull(link)) {
                throw new RuntimeException("Link not found");
            }

            Comment comment = commentMapper.toComment(commentDto);
            comment.setUser(user);
            comment.setLink(link);

            commentRepository.save(comment);
        }
    }

    public List<CommentDto> getComments(Long linkId) {
        return commentRepository.findAllByLinkId(linkId).stream()
                .map(commentMapper::toCommentDto)
                .toList();
    }
}
