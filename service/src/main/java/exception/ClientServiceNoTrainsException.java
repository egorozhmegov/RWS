package exception;

/**
 *Client no trains service exception.
 */
public class ClientServiceNoTrainsException extends ClientServiceException{
    public ClientServiceNoTrainsException(String message) {
        super(message);
    }
}
