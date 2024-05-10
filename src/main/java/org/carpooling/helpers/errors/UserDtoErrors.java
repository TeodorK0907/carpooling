package org.carpooling.helpers.errors;

public class UserDtoErrors {
    public static final String FIRSTNAME = "Field should be between 2 and 20 symbols.";
    public static final String LASTNAME = "Field should be between 2 and 20 symbols.";
    public static final String EMAIL = "Please enter a valid email address.";
    public static final String PHONE_NUMBER_LENGTH = "Phone number must be 10 digits long.";
    public static final String PHONE_NUMBER = "Phone number must include only digits.";
    public static final String USERNAME = "Username must be of between 2 to 20 symbols long with no special characters";
    public static final String PASSWORD = "Password must be at least 8 and up to 20 symbols long, " +
            "containing at least 1 uppercase, 1 lowercase, 1 special character and 1 digit.";

}