package divar.dto.response;

import divar.enums.UserRole;

public class LoginResponse {

    private String token;

    private Long userId;

    private String username;

    private UserRole role;
}
