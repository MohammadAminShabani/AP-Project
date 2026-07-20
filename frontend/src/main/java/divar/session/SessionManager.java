package divar.session;

public class SessionManager {

    private static String token;

    private static Long userId;

    private static String username;

    private static String role;


    private SessionManager() {
    }

    public static void setToken(String jwt) {
        token = jwt;
    }

    public static String getToken() {
        return token;
    }

    public static void setUserId(Long id) {
        userId = id;
    }

    public static Long getUserId() {
        return userId;
    }

    public static void setUsername(String name) {
        username = name;
    }

    public static String getUsername() {
        return username;
    }

    public static void setRole(String role){
        SessionManager.role = role;
    }

    public static String getRole(){
        return role;
    }

    public static boolean isLoggedIn() {
        return token != null && !token.isBlank();
    }

    public static void clear() {
        token = null;
        userId = null;
        username = null;
        role = null;
    }
}