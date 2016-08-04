package com.javaclasses.todolistapp.dto;

/**
 * Contains registration parameters
 */
public class RegistrationDto {

    private final String email;
    private final String password;
    private final String confirmPassword;

    public RegistrationDto(String email, String password, String confirmPassword) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RegistrationDto that = (RegistrationDto) o;

        return email.equals(that.email) && password.equals(that.password)
                && confirmPassword.equals(that.confirmPassword);

    }

    @Override
    public int hashCode() {
        int result = email.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + confirmPassword.hashCode();
        return result;
    }
}
