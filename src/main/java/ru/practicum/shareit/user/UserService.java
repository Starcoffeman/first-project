package ru.practicum.shareit.user;

import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();

    UserDto saveUser(UserDto userDto);

    UserDto getUserById(long userId);

    UserDto update(long userId, UserDto userDto);

    void deleteUserById(long userId);

}
