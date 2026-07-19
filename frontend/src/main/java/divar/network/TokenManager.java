package divar.network;

public class TokenManager {

    private static String token;

    private TokenManager() {
    }

    public static void saveToken(String jwt) {
        token = jwt;
    }

    public static String getToken() {
        return token;
    }

    public static void clearToken() {
        token = null;
    }

    public static boolean isLoggedIn() {
        return token != null && !token.isBlank();
    }
}