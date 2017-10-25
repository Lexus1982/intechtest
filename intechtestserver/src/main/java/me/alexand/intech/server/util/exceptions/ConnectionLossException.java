package me.alexand.intech.server.util.exceptions;

/**
 * @author asidorov84@gmail.com
 */
public class ConnectionLossException extends RuntimeException {
    public ConnectionLossException(Throwable cause) {
        super(cause);
    }
}
