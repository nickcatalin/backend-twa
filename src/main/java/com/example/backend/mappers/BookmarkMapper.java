package com.example.backend.mappers;

import com.example.backend.dtos.BookmarkDto;
import com.example.backend.entites.Bookmark;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookmarkMapper {
    BookmarkDto toBookmarkDto(Bookmark bookmark);

    Bookmark toBookmark(BookmarkDto bookmarkDto);
}
