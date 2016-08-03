package com.javaclasses.todolistapp.dto;

/**
 * Contains login parameters
 */
public class LoginDto {

    private final String email;
    private final String password;


    public LoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoginDto loginDto = (LoginDto) o;

        if (!email.equals(loginDto.email)) return false;
        return password.equals(loginDto.password);

    }

    @Override
    public int hashCode() {
        int result = email.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }
}
