package org.carpooling.models.input_dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.carpooling.helpers.error_helpers.InputErrors;
import org.carpooling.helpers.error_helpers.UserDtoErrors;

public class UserDto extends UpdateUserDto {

    @NotNull(message = InputErrors.FIELD_EMPTY)
    @Pattern(regexp = "^[a-zA-Z0-9]{2,20}$",
            message = UserDtoErrors.USERNAME)
    private String username;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,20}$",
            message = UserDtoErrors.PASSWORD)
    @NotNull(message = InputErrors.FIELD_EMPTY)
    private String password;

    public UserDto() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
