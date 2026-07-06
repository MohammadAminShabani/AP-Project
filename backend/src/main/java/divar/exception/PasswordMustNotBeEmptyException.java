package divar.exception;

public class PasswordMustNotBeEmptyException extends RuntimeException {
    public PasswordMustNotBeEmptyException(String message) {
        super(message);
    }
}
