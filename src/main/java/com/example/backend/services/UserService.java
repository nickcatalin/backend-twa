package com.example.backend.services;

import com.example.backend.dtos.CredentialsDto;
import com.example.backend.dtos.SignUpDto;
import com.example.backend.dtos.UserDto;
import com.example.backend.dtos.UserEditDto;
import com.example.backend.entites.Role;
import com.example.backend.entites.User;
import com.example.backend.exceptions.AppException;
import com.example.backend.mappers.UserMapper;
import com.example.backend.repositories.CommentRepository;
import com.example.backend.repositories.LinkRepository;
import com.example.backend.repositories.RatingRepository;
import com.example.backend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final LinkRepository linkRepository;

    private final CommentRepository commentRepository;

    private final RatingRepository ratingRepository;

    private final UserMapper userMapper;

    public UserDto login(CredentialsDto credentialsDto) {
        User user = userRepository.findByLogin(credentialsDto.getLogin())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), user.getPassword())) {
            return userMapper.toUserDto(user);
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    public UserDto register(SignUpDto userDto) {
        Optional<User> optionalUser = userRepository.findByLogin(userDto.getLogin());

        if (optionalUser.isPresent()) {
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.signUpToUser(userDto);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.getPassword())));

        Set<Role> roles = new HashSet<>();
        roles.add(new Role(2L, "USER"));

        user.setRoles(roles);

        User savedUser = userRepository.save(user);

        return userMapper.toUserDto(savedUser);
    }

    public UserDto findByLogin(String login) {
        return userRepository.findByLogin(login)
                .map(user -> new UserDto(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getLogin(),
                        null,
                        new HashSet<>(user.getRoles())
                ))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with login: " + login));
    }


    public List<UserEditDto> getUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserEditDto)
                .collect(Collectors.toList());
    }

    public void deleteUser(Long id) {
        commentRepository.deleteByUserId(id);
        ratingRepository.deleteByUserId(id);
        linkRepository.deleteByUserId(id);
        userRepository.deleteById(id);
    }

    public UserEditDto getUser(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toUserEditDto)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
    }

    public void updateUser(Long id, UserEditDto userEditDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        user.setFirstName(userEditDto.getFirstName());
        user.setLastName(userEditDto.getLastName());
        user.setLogin(userEditDto.getLogin());
        user.setRoles(userEditDto.getRoles().stream()
                .map(role -> new Role(role.getId(), role.getName()))
                .collect(Collectors.toSet()));

        userRepository.save(user);
    }
}
