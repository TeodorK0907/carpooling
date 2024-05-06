package org.carpooling.models.input_dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.carpooling.helpers.error_helpers.InputErrors;
import org.carpooling.helpers.error_helpers.UserDtoErrors;

public class UpdateUserDto {

    @NotNull(message = InputErrors.FIELD_EMPTY)
    @Size(min = 2, max = 20, message = UserDtoErrors.FIRSTNAME)
    private String firstName;

    @NotNull(message = InputErrors.FIELD_EMPTY)
    @Size(min = 2, max = 20, message = UserDtoErrors.LASTNAME)
    private String lastName;

    @Email(
            message = UserDtoErrors.EMAIL,
            regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"
    )
    @NotNull(message = InputErrors.FIELD_EMPTY)
    private String email;

    @Pattern(regexp = "^[0-9]+$",
            message = UserDtoErrors.PHONE_NUMBER)
    @NotNull(message = InputErrors.FIELD_EMPTY)
    @Size(min = 10, max = 10, message = UserDtoErrors.PHONE_NUMBER_LENGTH)
    private String phoneNumber;

    public UpdateUserDto () {

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
