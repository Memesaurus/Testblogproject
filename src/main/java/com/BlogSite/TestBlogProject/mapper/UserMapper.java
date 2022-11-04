package com.BlogSite.TestBlogProject.mapper;

import com.BlogSite.TestBlogProject.dto.UserDto;
import com.BlogSite.TestBlogProject.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User userDtoToUser(UserDto userDto);
}
