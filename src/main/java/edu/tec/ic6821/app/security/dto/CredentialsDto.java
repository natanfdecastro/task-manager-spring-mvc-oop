package edu.tec.ic6821.app.security.dto;

import javax.validation.constraints.NotBlank;

public class CredentialsDto {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public CredentialsDto(@NotBlank String username, @NotBlank String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
