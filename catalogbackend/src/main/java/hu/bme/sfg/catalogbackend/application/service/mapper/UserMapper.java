package hu.bme.sfg.catalogbackend.application.service.mapper;

import hu.bme.sfg.catalogbackend.application.dto.UserDto;
import hu.bme.sfg.catalogbackend.domain.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userToUserDto(User user);

    User userDtoToUser(UserDto userDto);
}
