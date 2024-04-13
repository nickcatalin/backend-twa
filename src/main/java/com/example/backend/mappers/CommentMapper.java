package com.example.backend.mappers;

import com.example.backend.dtos.CommentDto;
import com.example.backend.entites.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentDto toCommentDto(Comment comment);

    Comment toComment(CommentDto commentDto);
}
