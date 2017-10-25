package me.alexand.intech.server.util;

import javafx.util.Pair;
import me.alexand.intech.server.model.Command;

/**
 * @author asidorov84@gmail.com
 */
public interface CommandUtils {
    static Pair<Command, String> parseAction(String action) {
        if (action == null)
            throw new IllegalArgumentException("Action can not be null");

        if (action.matches("[A-Z]+\\?{1}[A-Z]+$")) {
            String[] arr = action.split("\\?");
            return new Pair<>(Command.valueOf(arr[0]), arr[1]);
        }

        if (action.matches("[A-Z]+$")) {
            return new Pair<>(Command.valueOf(action), "");
        }

        throw new IllegalArgumentException("Invalid action format: " + action);
    }
}
