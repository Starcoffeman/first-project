package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users =repository.findAll();
        return UserMapper.mapToUserDto(users);
    }

    @Override
    @Transactional
    public UserDto saveUser(UserDto userDto) {
        if (repository.existsByEmail(userDto.getEmail())) {
            User user = repository.save(UserMapper.mapToNewUser(userDto));
            throw new RuntimeException("Email address already exists");
        }
        User user = repository.save(UserMapper.mapToNewUser(userDto));
        return UserMapper.mapToUserDto(user);
    }

    @Override
    @Transactional
    public UserDto getUserById(long userId) {

        User user = repository.findById(userId)
                .orElseThrow(() -> new ValidationException("User not found with ID: " + userId));

        return UserMapper.mapToUserDto(user);
    }

    @Override
    public UserDto update(long userId, UserDto userDto) {
        User updatedUser = repository.findById(userId)
                .orElseThrow(() -> new ValidationException("User not found with ID: " + userId));
        // Check if the updated email already exists for another user
        if (repository.existsByEmailAndIdNot(userDto.getEmail(), userId)) {
            throw new ValidationException("Email address already exists: " + userDto.getEmail());
        }

        if (userDto.getName() != null) {
            updatedUser.setName(userDto.getName());
        }
        if (userDto.getEmail() != null) {
            updatedUser.setEmail(userDto.getEmail());
        }
        // Update the user using a custom query
        repository.updateUser(userId, updatedUser.getName(), updatedUser.getEmail());

        return UserMapper.mapToUserDto(updatedUser);
    }

}
