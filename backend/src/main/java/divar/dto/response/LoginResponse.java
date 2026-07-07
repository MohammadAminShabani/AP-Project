package divar.dto.response;

import divar.enums.UserRole;

public class LoginResponse {

    private String token;

    private Long userId;

    private String username;

    private UserRole role;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public Long getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }

    public UserRole getRole() {
        return role;
    }
}
