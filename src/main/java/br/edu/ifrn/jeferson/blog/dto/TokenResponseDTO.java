package br.edu.ifrn.jeferson.blog.dto;

public class TokenResponseDTO {
    private String tokenType = "Bearer";
    private String accessToken;

    public TokenResponseDTO() {}
    public TokenResponseDTO(String accessToken) { this.accessToken = accessToken; }

    public String getTokenType() { return tokenType; }
    public void setTokenType(String tokenType) { this.tokenType = tokenType; }

    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
}
