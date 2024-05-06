package org.carpooling.mappers;

import org.carpooling.helpers.UserRole;
import org.carpooling.models.User;
import org.carpooling.models.input_dto.UpdateUserDto;
import org.carpooling.models.input_dto.UserDto;
import org.carpooling.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final UserService userService;

    @Autowired
    public UserMapper (UserService userService) {
        this.userService = userService;
    }

    public User toObj (UserDto dto) {
        User user = new User();
        populateUserObj(user, dto);
        return user;
    }

    public User toObj(int id, UpdateUserDto dto) {
        User user = userService.getById(id);
        populateUserObj(user, dto);
        return user;
    }

    private void populateUserObj(User user, UserDto dto) {
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setEmail(dto.getEmail());
        user.setRole(UserRole.USER);
    }

    private void populateUserObj(User user, UpdateUserDto dto) {
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setEmail(dto.getEmail());
    }
}
