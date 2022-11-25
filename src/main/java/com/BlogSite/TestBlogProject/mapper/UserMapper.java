package com.BlogSite.TestBlogProject.mapper;

import com.BlogSite.TestBlogProject.dto.LoginDto;
import com.BlogSite.TestBlogProject.dto.UserDto;
import com.BlogSite.TestBlogProject.models.Role;
import com.BlogSite.TestBlogProject.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", source = "role")
    User loginDtoToUser(LoginDto loginDto, Role role);

    List<UserDto> userListToUserDtoList(List<User> userList);
}
