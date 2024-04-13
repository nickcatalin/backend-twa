package com.example.backend.mappers;

import com.example.backend.dtos.LinkDto;
import com.example.backend.entites.Link;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface LinkMapper {

    LinkDto toLinkDto(Link link);

    Link toLink(LinkDto linkDto);
}
