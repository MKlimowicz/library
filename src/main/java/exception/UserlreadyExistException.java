package exception;

public class UserlreadyExistException extends RuntimeException {
    public UserlreadyExistException(String message) {
        super(message);
    }
}
