package me.alexand.intech.client.controller;

/**
 * @author asidorov84@gmail.com
 */
public class ConnectionLossException extends RuntimeException {
    public ConnectionLossException(Throwable cause) {
        super(cause);
    }
}
