package divar.util;

public final class Constants {

    private Constants() {}

    // =======================
    // Base URL
    // =======================

    public static final String BASE_URL = "http://localhost:8080";

    // =======================
    // FXML Files
    // =======================

    public static final String LOGIN = "Login.fxml";
    public static final String REGISTER = "Register.fxml";
    public static final String HOME = "Home.fxml";
    public static final String PROFILE = "Profile.fxml";
    public static final String FAVORITES = "Favorites.fxml";
    public static final String CHAT = "Chat.fxml";
    public static final String CONVERSATIONS = "Conversations.fxml";
    public static final String ADMIN = "AdminDashboard.fxml";
    public static final String CREATE_AD = "CreateAdvertisement.fxml";
    public static final String EDIT_AD = "EditAdvertisement.fxml";
    public static final String ADVERTISEMENT = "Advertisement.fxml";
    public static final String RATING = "Rating.fxml";

    // =======================
    // API Endpoints
    // =======================

    public static final String LOGIN_API = "/users/login";
    public static final String REGISTER_API = "/users/register";
    public static final String ADVERTISEMENT_API = "/advertisements";
    public static final String FAVORITE_API = "/favorites";
    public static final String CONVERSATION_API = "/conversations";
    public static final String MESSAGE_API = "/messages";
    public static final String RATING_API = "/ratings";
    public static final String ADMIN_API = "/admin";
}