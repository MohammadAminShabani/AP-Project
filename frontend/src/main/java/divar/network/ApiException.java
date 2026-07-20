package divar.network;

/**
 * Thrown when the backend returns a non-2xx response.
 * message is already a clean, user-friendly string
 * (parsed out of the backend's JSON or plain-text error body).
 */
public class ApiException extends Exception {

    private final int statusCode;

    public ApiException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
