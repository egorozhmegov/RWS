package exception;

public class UserServiceInvalidDataException extends ServiceException {
    public UserServiceInvalidDataException(String message) {
        super(message);
    }
}
