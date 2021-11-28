package hu.bme.sfg.catalogbackend.application.service;

import hu.bme.sfg.catalogbackend.application.dto.RegisterDto;
import hu.bme.sfg.catalogbackend.application.dto.UserDto;
import hu.bme.sfg.catalogbackend.application.service.mapper.UserMapper;
import hu.bme.sfg.catalogbackend.domain.User;
import hu.bme.sfg.catalogbackend.repository.UserRepository;
import hu.bme.sfg.catalogbackend.util.UserException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private UserMapper userMapper;

    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto register(RegisterDto registerDto) throws UserException {
        if(!userRepository.findByUserName(registerDto.getUserName()).isPresent() || !userRepository.findByEmail(registerDto.getEmail()).isPresent()){
            User user = User.builder()
                    .userName(registerDto.getUserName())
                    .email(registerDto.getEmail())
                    .password(passwordEncoder.encode(registerDto.getPassword()))
                    .build();
            userRepository.save(user);
            log.info("User is created");
            return userMapper.userToUserDto(user);
        }else {
            log.error("Username or email is not unique");
            throw new UserException("Username or email is not unique");
        }
    }
}
