package com.example.uade.tpo.service;

import com.example.uade.tpo.Utils.Mapper;
import com.example.uade.tpo.dtos.request.ChangeRoleRequestDto;
import com.example.uade.tpo.dtos.response.UserResponseDto;
import com.example.uade.tpo.entity.Role;
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

    public List<UserResponseDto> getUsers() {
        return userRepository.findAll().stream().map(Mapper::convertToUserResponseDto).collect(Collectors.toList());
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public boolean changeRole(Long userId, ChangeRoleRequestDto request) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            String newRole = request.getRole();
            if (newRole.equals("ROLE_ADMIN") || newRole.equals("ROLE_USER")) {
                user.get().setRole(Role.valueOf(newRole));
                userRepository.save(user.get());
                return true;
            }
        }
        return false;
    }

}
