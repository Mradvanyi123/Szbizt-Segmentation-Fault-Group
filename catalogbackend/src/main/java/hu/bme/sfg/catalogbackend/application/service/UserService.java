package hu.bme.sfg.catalogbackend.application.service;

import hu.bme.sfg.catalogbackend.application.dto.RegisterDto;
import hu.bme.sfg.catalogbackend.application.dto.UserDto;
import hu.bme.sfg.catalogbackend.util.UserException;

public interface UserService {
    UserDto register(RegisterDto registerDto) throws UserException;
}
