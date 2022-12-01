package com.BlogSite.TestBlogProject.mapper;

import com.BlogSite.TestBlogProject.dto.BlogMessageDto;
import com.BlogSite.TestBlogProject.models.Comment;
import com.BlogSite.TestBlogProject.models.Post;
import com.BlogSite.TestBlogProject.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BlogMessageMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "user")
    Post blogMessageDtoToPost(BlogMessageDto blogMessageDto, User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "user")
    Comment blogMessageDtoToComment(BlogMessageDto blogMessageDto, User user, Long parentId);
}
