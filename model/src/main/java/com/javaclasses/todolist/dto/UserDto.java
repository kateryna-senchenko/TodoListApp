package com.javaclasses.todolist.dto;

import com.javaclasses.todolist.tinytypes.UserId;

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

        if (!userId.equals(userDto.userId)) return false;
        return email.equals(userDto.email);

    }

    @Override
    public int hashCode() {
        int result = userId.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }
}
