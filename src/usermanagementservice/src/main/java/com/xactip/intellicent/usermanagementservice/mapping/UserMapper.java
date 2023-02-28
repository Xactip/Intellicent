package com.xactip.intellicent.usermanagementservice.mapping;

import com.xactip.intellicent.usermanagementservice.dto.UserDto;
import com.xactip.intellicent.usermanagementservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    UserDto entityToDTO(User user);

    List<UserDto> entityToDTO(Iterable<User> users);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    User dtoToEntity(UserDto user);

    List<User> dtoToEntity(Iterable<UserDto> users);
}
