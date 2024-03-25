package com.reactive.reactiveflash.api.mapper;

import com.reactive.reactiveflash.api.controller.request.UserRequest;
import com.reactive.reactiveflash.api.controller.response.UserResponse;
import com.reactive.reactiveflash.domain.document.UserDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    UserDocument toDocument(final UserRequest request);

    UserResponse toResponse(final UserDocument document);
}
