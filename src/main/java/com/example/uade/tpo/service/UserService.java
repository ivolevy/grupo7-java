package com.example.uade.tpo.service;

import com.example.uade.tpo.dtos.request.CreateUserRequestDto;
import com.example.uade.tpo.dtos.response.UserResponseDto;
import com.example.uade.tpo.entity.User;
import com.example.uade.tpo.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;

    public List<UserResponseDto> getUsuarios() {
        return userRepository.findAll().stream().map(this::convertToResponseDto).collect(Collectors.toList());
    }

    public Optional<UserResponseDto> getUserById(Long userId) {
        return userRepository.findById(userId).map(this::convertToResponseDto);
    }

    public UserResponseDto createUser(CreateUserRequestDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        return convertToResponseDto(userRepository.save(user));
    }

    public UserResponseDto updateUser(Long userId, CreateUserRequestDto userDetails) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setName(userDetails.getName());
            user.setLastName(userDetails.getLastName());
            user.setEmail(userDetails.getEmail());
            user.setPassword(userDetails.getPassword());
            return convertToResponseDto(userRepository.save(user));
        }
        return null;
    }

    public Boolean deleteUser(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    private UserResponseDto convertToResponseDto(User user) {
        UserResponseDto userDto = new UserResponseDto();
        userDto.setUserId(user.getId());
        userDto.setName(user.getName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

}
