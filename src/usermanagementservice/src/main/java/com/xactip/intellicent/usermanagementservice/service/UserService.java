package com.xactip.intellicent.usermanagementservice.service;

import com.xactip.intellicent.usermanagementservice.dto.UserDto;
import com.xactip.intellicent.usermanagementservice.exception.UserNotFoundException;
import com.xactip.intellicent.usermanagementservice.mapping.UserMapper;
import com.xactip.intellicent.usermanagementservice.model.User;
import com.xactip.intellicent.usermanagementservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    public List<UserDto> getAllUsers() {
        List<User> users = repository.findAll();
        return mapper.entityToDTO(users);
    }

    public UserDto registerUser(String userId, UserDto userDto) {
        User user = mapper.dtoToEntity(userDto);
        user.setId(userId);
        User savedUser = repository.save(user);
        return mapper.entityToDTO(savedUser);
    }

    public UserDto getUserById(String userId) {
        return repository.findById(userId)
                .map(mapper::entityToDTO)
                .orElseThrow(() -> new UserNotFoundException("User not found by ID " + userId));
    }
}
