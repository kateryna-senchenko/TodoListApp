package com.javaclasses.todolistapp.dto;

import com.javaclasses.todolistapp.tinytypes.UserId;

/**
 * User DTO
 */
public class UserDto {

    private final UserId userId;
    private final String email;

    public UserDto(UserId userId, String email) {
        this.userId = userId;
        this.email = email;
    }

    public UserId getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDto userDto = (UserDto) o;

        return userId.equals(userDto.userId) && email.equals(userDto.email);

    }

    @Override
    public int hashCode() {
        int result = userId.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }
}
