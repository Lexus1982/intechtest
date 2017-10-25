package me.alexand.intech.server.util.exceptions;

/**
 * @author asidorov84@gmail.com
 */
public class IllegalFSMEvent extends Exception {
    public IllegalFSMEvent(Throwable cause) {
        super(cause);
    }

    public IllegalFSMEvent(String message) {
        super(message);
    }
}
