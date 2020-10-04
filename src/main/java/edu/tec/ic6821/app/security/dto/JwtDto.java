package edu.tec.ic6821.app.security.dto;

public class JwtDto {
    private static final String TOKEN_TYPE_BEARER = "Bearer";

    private String token;

    public JwtDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public String getTokenType() {
        return TOKEN_TYPE_BEARER;
    }
}
