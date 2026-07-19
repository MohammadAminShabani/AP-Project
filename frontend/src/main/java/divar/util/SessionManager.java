package divar.util;

public class SessionManager {

    private static String token;

    private static Long userId;

    private static String username;

    private static String role;

    private SessionManager(){}

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        SessionManager.token = token;
    }

    public static Long getUserId() {
        return userId;
    }

    public static void setUserId(Long userId) {
        SessionManager.userId = userId;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        SessionManager.username = username;
    }

    public static String getRole() {
        return role;
    }

    public static void setRole(String role) {
        SessionManager.role = role;
    }

    public static boolean isLoggedIn(){

        return token != null && !token.isBlank();
    }

    public static void logout(){

        token=null;
        userId=null;
        username=null;
        role=null;
    }

}